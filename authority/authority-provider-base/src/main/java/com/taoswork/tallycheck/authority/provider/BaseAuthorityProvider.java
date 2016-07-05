package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;
import com.taoswork.tallycheck.authority.core.IllegalCodePathException;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.provider.resource.KResourceAccess;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionLink;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;

/**
 * Created by gaoyuan on 7/3/16.
 */
public abstract class BaseAuthorityProvider implements IAuthorityProvider {
    public BaseAuthorityProvider() {
    }

    protected abstract IKProtectionMapping getProtectionMapping(ProtectionScope scope);

    protected String correctResource(ProtectionScope scope, String resource){
        IKProtectionMapping mapping = this.getProtectionMapping(scope);
        if(mapping != null){
            return mapping.correctResource(resource);
        }
        return resource;
    }

    @Override
    public final ResProtectionWithPermission getProtectionWithPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        IKPermission permission = this.getPermission(scope, resourceTypeName, userId);
        ResProtection protection = this.getProtection(scope, resourceTypeName);
        return new ResProtectionWithPermission(protection, permission);
    }

    @Override
    public final IKPermission getPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        String correctedResourceType = correctResource(scope, resourceTypeName);
        correctedResourceType = ResourceUtility.unifiedResourceName(correctedResourceType);
        return doGetPermission(scope, correctedResourceType, userId);
    }

    @Override
    public final ResProtection getProtection(ProtectionScope scope, String resourceTypeName) {
        String correctedResourceType = correctResource(scope, resourceTypeName);
        correctedResourceType = ResourceUtility.unifiedResourceName(correctedResourceType);
        return doGetProtection(scope, correctedResourceType);
    }

    protected abstract IKPermission doGetPermission(ProtectionScope scope, String resourceTypeName, String userId) ;

    protected abstract ResProtection doGetProtection(ProtectionScope scope, String resourceTypeName);

    @Override
    public final boolean canAccessMappedResource(ProtectionScope scope, String virtualResource, Access requiredAccess, String userId) {
        IKProtectionMapping mapping = getProtectionMapping(scope);

        final KResourceAccess rcAccess = new KResourceAccess(virtualResource, requiredAccess);
        IKProtectionLink protection = mapping.getLink(rcAccess);
        if (protection == null)
            return false;

        IKPermission entityPermission = getPermission(scope, protection.getActualResource(), userId);
        if (entityPermission == null) {
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        switch (protection.getProtectionMode()) {
            case FitAll:
                return mergedAccess.hasAccess(protection.getActualAccess());
            case FitAny:
                return mergedAccess.hasAnyAccess(protection.getActualAccess());
            default:
                throw new IllegalCodePathException("Access Verifier error.");
        }
    }
}
