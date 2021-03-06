package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermission;

/**
 * Created by gaoyuan on 7/5/16.
 */
public class AllBlockAuthorityProvider implements IAuthorityProvider{
    @Override
    public ResProtection getProtection(ProtectionScope scope, String resourceTypeName) {
        ResProtection resProtection = new ResProtection();
        resProtection.resource = resourceTypeName;
        resProtection.masterControlled = true;
        resProtection.protectionMode = ProtectionMode.FitAll;
        return resProtection;
    }

    @Override
    public IKPermission getPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        KPermission permission = new KPermission(resourceTypeName);
        permission.setMasterAccess(Access.None);
        return permission;
    }

    @Override
    public final ResProtectionWithPermission getProtectionWithPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        IKPermission permission = this.getPermission(scope, resourceTypeName, userId);
        ResProtection protection = this.getProtection(scope, resourceTypeName);
        return new ResProtectionWithPermission(protection, permission);
    }

    @Override
    public boolean canAccessMappedResource(ProtectionScope scope, String virtualResource, Access requiredAccess, String userId) {
        return false;
    }
}
