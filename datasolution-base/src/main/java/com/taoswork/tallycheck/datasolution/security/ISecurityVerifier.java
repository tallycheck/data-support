package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.NoPermissionException;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface ISecurityVerifier {
    public static final String COMPONENT_NAME = "ISecurityVerifier";

    Access getAllPossibleAccess(SecurityAccessor accessor, String resourceEntity, Access mask);

    boolean canAccess(SecurityAccessor accessor, String resourceEntity, Access access);

    boolean canAccess(SecurityAccessor accessor, String resourceEntity, Access access, Object... instances);

    void checkAccess(SecurityAccessor accessor, String resourceEntity, Access access) throws NoPermissionException;

    void checkAccess(SecurityAccessor accessor, String resourceEntity, Access access, Object... instances) throws NoPermissionException;

    boolean canAccessMappedResource(SecurityAccessor accessor, String mappedResource, Access access);

    void setAuthorityProvider(IAuthorityProvider provider);
}