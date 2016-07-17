package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.domain.ProtectionSpec;
import com.taoswork.tallycheck.authority.domain.resource.DProtectionMode;
import com.taoswork.tallycheck.authority.domain.resource.Protection;
import com.taoswork.tallycheck.authority.domain.resource.ProtectionCase;
import com.taoswork.tallycheck.authority.domain.user.GroupAuthority;
import com.taoswork.tallycheck.authority.domain.user.UserAuthority;
import com.taoswork.tallycheck.authority.provider.permission.IKAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyuan on 7/2/16.
 */
public class AuthorityProviderImpl extends BaseAuthorityProvider {

    private final MongoEntityService entityService;
    private final Datastore datastore;

    private final Class<? extends ProtectionSpec> psClz;
    private final Class<? extends Protection> pClz;
    private final Class<? extends UserAuthority> userClz;
    private final Class<? extends GroupAuthority> groupClz;

    public AuthorityProviderImpl(MongoEntityService entityService,
                                 Class<? extends UserAuthority> userClz,
                                 Class<? extends GroupAuthority> groupClz) {
        this(entityService, ProtectionSpec.class, Protection.class,
                userClz, groupClz);
    }

    public AuthorityProviderImpl(MongoEntityService entityService,
                                 Class<? extends ProtectionSpec> protectionSpecClz,
                                 Class<? extends Protection> protectionClz,
                                 Class<? extends UserAuthority> userClz,
                                 Class<? extends GroupAuthority> groupClz) {
        super();
        this.entityService = entityService;
        this.datastore = entityService.getDatastore();
        this.psClz = protectionSpecClz;
        this.pClz = protectionClz;
        this.userClz = userClz;
        this.groupClz = groupClz;
    }

    @Override
    protected IKProtectionMapping getProtectionMapping(ProtectionScope scope) {
        String spec = scope.spec;
        Query<? extends ProtectionSpec> q = datastore.createQuery(psClz);
        q.filter(ProtectionSpec.FN_SPEC_NAME, spec);
        ProtectionSpec ps = q.get();
        if (ps != null)
            return PermissionObjectMaker.convert(ps);
        return null;
    }


    protected IKAuthority getAuthority(String protectionSpec, String region, String userId) {
        Query<? extends UserAuthority> q = datastore.createQuery(userClz);
        q.filter(UserAuthority.FN_PROTECTION_SPEC, protectionSpec)
                .filter(UserAuthority.FN_PROTECTION_REGION, region)
                .filter(UserAuthority.FN_OWNER_ID, userId);
        UserAuthority ua = q.get();
        if (ua != null) {
            ISimpleKAuthority kua = PermissionObjectMaker.makeAuthority(ua);
            Collection<? extends GroupAuthority> gas = ua.theGroups();
            for (GroupAuthority ga : gas) {
                if (ga != null) {
                    ISimpleKAuthority kga = PermissionObjectMaker.makeAuthority(ga);
                    kua.merge(kga);
                }
            }
            return kua;
        }
        return null;
    }

    private ISimpleKAuthority getRawGroupAuthority(String protectionSpec, String protectionRegion, String groupId) {
        Query<? extends GroupAuthority> q = datastore.createQuery(groupClz);
        q.filter(UserAuthority.FN_PROTECTION_SPEC, protectionSpec)
                .filter(UserAuthority.FN_PROTECTION_REGION, protectionRegion)
                .filter(UserAuthority.FN_OWNER_ID, groupId);
        GroupAuthority ga = q.get();
        if (ga != null) {
            return PermissionObjectMaker.makeAuthority(ga);
        }
        return null;
    }

    private IKAuthority superUserAuthority;
    private IKAuthority getSuperUserAuthority(ProtectionScope scope){
        if(superUserAuthority != null){
            return superUserAuthority;
        }
        Query<? extends Protection> q = datastore.createQuery(pClz);
        q.filter(Protection.FN_PROTECTION_SPEC, scope.spec)
                .filter(Protection.FN_PROTECTION_REGION, scope.region);
        List<? extends Protection> protections = q.asList();
        superUserAuthority = PermissionObjectMaker.makeFullAuthority(scope, protections, "superuser");
        return superUserAuthority;
    }

    @Override
    protected ResProtection doGetProtection(ProtectionScope scope, String resourceTypeName) {
        Query<? extends Protection> q = datastore.createQuery(pClz);
        q.filter(Protection.FN_PROTECTION_SPEC, scope.spec)
                .filter(Protection.FN_PROTECTION_REGION, scope.region)
                .filter(Protection.FN_RESOURCE_ENTITY, resourceTypeName).limit(1);
        List<? extends Protection> protections = q.asList();
        if (protections != null && protections.size() == 1) {
            Protection protection = protections.get(0);
            return convert(protection);
        }
        return null;
    }

    @Override
    protected IKPermission doGetPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        IKAuthority userAuthority = getAuthority(scope, userId);
        if (userAuthority != null) {
            return userAuthority.getPermission(resourceTypeName);
        }
        return null;
    }

    protected static ResProtection convert(Protection protection) {
        ResProtection resProtection = new ResProtection();
        resProtection.resource = protection.getResource();
        resProtection.masterControlled = protection.isMasterControlled();
        resProtection.protectionMode = DProtectionMode.toNativeType(protection.getProtectionMode());
        Map<String, ProtectionCase> cases = protection.getCases();
        if (cases != null && cases.size() > 0) {
            List<ResProtectionCase> casesList = new ArrayList<ResProtectionCase>();
            for (ProtectionCase _case : cases.values()) {
                ResProtectionCase _resCase = new ResProtectionCase();
                _resCase.code = _case.getUuid();
                _resCase.name = _case.getName();
                _resCase.filterType = _case.getFilter();
                _resCase.filterParameter = _case.getFilterParameter();
                casesList.add(_resCase);
            }
            resProtection.cases = casesList;
        } else {
            resProtection.cases = null;
        }
        resProtection.version = protection.getVersion();
        return resProtection;
    }

    private IKAuthority getAuthority(ProtectionScope scope, String userId) {
        String spec = scope.spec;
        String region = scope.region;

        Query<? extends UserAuthority> q = datastore.createQuery(userClz);
        q.filter(UserAuthority.FN_PROTECTION_SPEC, spec)
                .filter(UserAuthority.FN_PROTECTION_REGION, region)
                .filter(UserAuthority.FN_OWNER_ID, userId);
        UserAuthority ua = q.get();
        if (ua != null) {
            if(ua.isSuperUser()) {
                return getSuperUserAuthority(scope);
            }
            ISimpleKAuthority kua = PermissionObjectMaker.makeAuthority(ua);
            Collection<? extends GroupAuthority> gas = ua.theGroups();
            if(gas != null && gas.size() > 0) {
                for (GroupAuthority ga : gas) {
                    if (ga != null) {
                        ISimpleKAuthority kga = PermissionObjectMaker.makeAuthority(ga);
                        kua.merge(kga);
                    }
                }
            }
            return kua;
        }
        return null;
    }

    private ISimpleKAuthority getRawGroupAuthority(ProtectionScope scope, String groupId) {
        String spec = scope.spec;
        String region = scope.region;

        Query<? extends GroupAuthority> q = datastore.createQuery(groupClz);
        q.filter(GroupAuthority.FN_PROTECTION_SPEC, spec)
                .filter(GroupAuthority.FN_PROTECTION_REGION, region)
                .filter(GroupAuthority.FN_OWNER_ID, groupId);
        GroupAuthority ga = q.get();
        if (ga != null) {
            return PermissionObjectMaker.makeAuthority(ga);
        }
        return null;
    }

}