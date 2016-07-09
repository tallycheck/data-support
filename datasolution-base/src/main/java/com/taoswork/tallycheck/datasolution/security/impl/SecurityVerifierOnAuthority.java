package com.taoswork.tallycheck.datasolution.security.impl;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.client.IAuthorityVerifier;
import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.client.impl.AuthorityVerifierImpl;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public final class SecurityVerifierOnAuthority
        extends BaseSecurityVerifier {

    @Resource(name = EntityFilterManager.COMPONENT_NAME)
    protected EntityFilterManager entityFilterManager;

    private IAuthorityVerifier authorityVerifier;

    public SecurityVerifierOnAuthority() {
        setAuthorityProvider(null);
    }

    @Override
    public void setAuthorityProvider(IAuthorityProvider provider) {
        authorityVerifier = new AuthorityVerifierImpl(provider, entityFilterManager);
    }

    @Override
    public Access getAllPossibleAccess(SecurityAccessor accessor, String resourceEntity, Access mask) {
        Access access = authorityVerifier.getAllPossibleAccess(accessor.protectionScope, accessor.userId, resourceEntity, mask);
        return access;
    }

    @Override
    public boolean canAccess(SecurityAccessor accessor, String resourceEntity, Access access) {
        return canAccess(accessor, resourceEntity, access, null);
    }

    @Override
    public boolean canAccess(SecurityAccessor accessor, String resourceEntity, Access access, Object... instances) {
        String userId = accessor.userId;
        ProtectionScope PS = accessor.protectionScope;
        if (instances == null || instances.length == 0) {
            boolean can = authorityVerifier.canAccess(PS, userId, resourceEntity, access);
            return can;
        } else {
            boolean can = authorityVerifier.canAccess(PS, userId, resourceEntity, access, instances);
            return can;
        }
    }

    @Override
    public boolean canAccessMappedResource(SecurityAccessor accessor,
                                           String mappedResource, Access access) {
        return authorityVerifier.canAccessMappedResource(accessor.protectionScope, accessor.userId,
                mappedResource, access);
    }
}