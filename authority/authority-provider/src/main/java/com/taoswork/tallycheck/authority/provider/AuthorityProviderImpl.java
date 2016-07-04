package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.domain.ProtectionSpace;
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
public class AuthorityProviderImpl extends BaseAuthorityProvider{

    private final MongoEntityService entityService;
    private final Datastore datastore;

    private final Class<? extends ProtectionSpace> psClz;
    private final Class<? extends Protection> pClz;
    private final Class<? extends UserAuthority> userClz;
    private final Class<? extends GroupAuthority> groupClz;

    public AuthorityProviderImpl(MongoEntityService entityService,
                            Class<? extends UserAuthority> userClz,
                            Class<? extends GroupAuthority> groupClz) {
        this(entityService, ProtectionSpace.class, Protection.class, userClz, groupClz);
    }

    public AuthorityProviderImpl(MongoEntityService entityService,
                            Class<? extends ProtectionSpace> protectionSpaceClz,
                            Class<? extends Protection> protectionClz,
                            Class<? extends UserAuthority> userClz,
                            Class<? extends GroupAuthority> groupClz) {
        this.entityService = entityService;
        this.datastore = entityService.getDatastore();
        this.psClz = protectionSpaceClz;
        this.pClz = protectionClz;
        this.userClz = userClz;
        this.groupClz = groupClz;
    }

    @Override
    protected IKProtectionMapping getProtectionMapping(ProtectionScope scope) {
        String protectionSpace = scope.protectionSpace;
        Query<? extends ProtectionSpace> q = datastore.createQuery(psClz);
        q.filter(ProtectionSpace.FN_SPACE_NAME, protectionSpace);
        ProtectionSpace ps = q.get();
        if (ps != null)
            return PermissionObjectMaker.convert(ps);
        return null;
    }


    protected IKAuthority getAuthority(String protectionSpace, String tenantId, String userId) {
        Query<? extends UserAuthority> q = datastore.createQuery(userClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, userId);
        UserAuthority ua = q.get();
        if(ua != null){
            ISimpleKAuthority kua = PermissionObjectMaker.makeAuthority(ua);
            Collection<? extends GroupAuthority> gas = ua.theGroups();
            for(GroupAuthority ga : gas){
                if(ga != null) {
                    ISimpleKAuthority kga = PermissionObjectMaker.makeAuthority(ga);
                    kua.merge(kga);
                }
            }
            return kua;
        }
        return null;
    }

    private ISimpleKAuthority getRawGroupAuthority(String protectionSpace, String tenantId, String groupId) {
        Query<? extends GroupAuthority> q = datastore.createQuery(groupClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, groupId);
        GroupAuthority ga = q.get();
        if(ga != null){
            return PermissionObjectMaker.makeAuthority(ga);
        }
        return null;
    }


    @Override
    protected ResProtection doGetProtection(ProtectionScope scope, String resourceTypeName) {
        Query<? extends Protection> q = datastore.createQuery(pClz);
        q.filter(Protection.FN_PROTECTION_SPACE, scope.protectionSpace)
                .filter(Protection.FN_NAMESPACE, scope.tenantId)
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
        return userAuthority.getPermission(resourceTypeName);
    }

    protected static ResProtection convert(Protection protection){
        ResProtection resProtection = new ResProtection();
        resProtection.resource = protection.getResource();
        resProtection.masterControlled = protection.isMasterControlled();
        resProtection.protectionMode = DProtectionMode.toNativeType(protection.getProtectionMode());
        Map<String, ProtectionCase> cases = protection.getCases();
        if(cases != null && cases.size() > 0){
            List<ResProtectionCase> casesList = new ArrayList<ResProtectionCase>();
            for (ProtectionCase _case : cases.values()){
                ResProtectionCase _resCase = new ResProtectionCase();
                _resCase.code = _case.getUuid();
                _resCase.name = _case.getName();
                _resCase.filterType = _case.getFilter();
                _resCase.filterParameter = _case.getFilterParameter();
                casesList.add(_resCase);
            }
            resProtection.cases = casesList;
        }else {
            resProtection.cases = null;
        }
        resProtection.version = protection.getVersion();
        return resProtection;
    }

    private IKAuthority getAuthority(ProtectionScope scope, String userId) {
        String protectionSpace = scope.protectionSpace;
        String tenantId = scope.tenantId;

        Query<? extends UserAuthority> q = datastore.createQuery(userClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, userId);
        UserAuthority ua = q.get();
        if(ua != null){
            ISimpleKAuthority kua = PermissionObjectMaker.makeAuthority(ua);
            Collection<? extends GroupAuthority> gas = ua.theGroups();
            for(GroupAuthority ga : gas){
                if(ga != null) {
                    ISimpleKAuthority kga = PermissionObjectMaker.makeAuthority(ga);
                    kua.merge(kga);
                }
            }
            return kua;
        }
        return null;
    }

    private ISimpleKAuthority getRawGroupAuthority(ProtectionScope scope, String groupId) {
        String protectionSpace = scope.protectionSpace;
        String tenantId = scope.tenantId;

        Query<? extends GroupAuthority> q = datastore.createQuery(groupClz);
        q.filter(UserAuthority.FN_PROTECTION_SPACE, protectionSpace)
                .filter(UserAuthority.FN_NAMESPACE, tenantId)
                .filter(UserAuthority.FN_OWNER_ID, groupId);
        GroupAuthority ga = q.get();
        if(ga != null){
            return PermissionObjectMaker.makeAuthority(ga);
        }
        return null;
    }

}
