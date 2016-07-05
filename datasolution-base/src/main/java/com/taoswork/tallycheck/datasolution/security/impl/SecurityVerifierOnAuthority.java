package com.taoswork.tallycheck.datasolution.security.impl;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.client.IAuthorityVerifier;
import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.client.impl.AuthorityVerifierImpl;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.datasolution.security.IProtectedAccessContext;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public final class SecurityVerifierOnAuthority
        extends BaseSecurityVerifier {

    @Resource(name = EntityFilterManager.COMPONENT_NAME)
    protected EntityFilterManager entityFilterManager;

    private IAuthorityVerifier authorityVerifier;

    private IProtectedAccessContext accessContext;

    public SecurityVerifierOnAuthority() {
        setAuthorityProvider(null);
    }

    @Override
    public void setAuthorityProvider(IAuthorityProvider provider) {
        authorityVerifier = new AuthorityVerifierImpl(provider, entityFilterManager);
    }

    @Override
    public void setAuthorityContext(IProtectedAccessContext accessContext) {
        this.accessContext = accessContext;
    }

    private IProtectedAccessContext vc(){
        if(accessContext == null){
            throw new RuntimeException("IProtectedAccessContext not set.");
        }
        return accessContext;
    }

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        String userId = vc().getCurrentUserId();
        ProtectionScope PS = vc().getCurrentProtectionScope();
        Access access = authorityVerifier.getAllPossibleAccess(PS, resourceEntity, userId);
        if (mask == null) {
            return access;
        } else {
            return mask.and(access);
        }
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        String userId = vc().getCurrentUserId();
        ProtectionScope PS = vc().getCurrentProtectionScope();
        boolean can = authorityVerifier.canAccess(PS, resourceEntity, access, userId);
        return can;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        String userId = vc().getCurrentUserId();
        ProtectionScope PS = vc().getCurrentProtectionScope();
        boolean can = authorityVerifier.canAccess(PS, resourceEntity, access, userId, instances);
        return can;
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        String userId = vc().getCurrentUserId();
        ProtectionScope PS = vc().getCurrentProtectionScope();
        return authorityVerifier.canAccessMappedResource(PS, mappedResource, access, userId);
    }
}