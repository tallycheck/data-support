package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;

/**
 * Created by gaoyuan on 7/6/16.
 */
public class AuthorityProviderDelegate implements IAuthorityProvider {
    private IAuthorityProvider authorityProvider;

    protected IAuthorityProvider getAuthorityProvider() {
        return authorityProvider;
    }

    protected void setAuthorityProvider(IAuthorityProvider authorityProvider) {
        this.authorityProvider = authorityProvider;
    }

    @Override
    public ResProtection getProtection(ProtectionScope scope, String resourceTypeName) {
        return authorityProvider.getProtection(scope, resourceTypeName);
    }

    @Override
    public IKPermission getPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        return authorityProvider.getPermission(scope, resourceTypeName, userId);
    }

    @Override
    public ResProtectionWithPermission getProtectionWithPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        return authorityProvider.getProtectionWithPermission(scope, resourceTypeName, userId);
    }

    @Override
    public boolean canAccessMappedResource(ProtectionScope scope, String virtualResource, Access requiredAccess, String userId) {
        return authorityProvider.canAccessMappedResource(scope, virtualResource, requiredAccess, userId);
    }
}
