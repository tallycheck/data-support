package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.dataservice.exception.NoPermissionException;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface ISecurityVerifier {
    public static final String COMPONENT_NAME = "ISecurityVerifier";

    Access getAllPossibleAccess(String resourceEntity, Access mask);

    boolean canAccess(String resourceEntity, Access access);

    boolean canAccess(String resourceEntity, Access access, Object... instances);

    void checkAccess(String resourceEntity, Access access) throws NoPermissionException;

    void checkAccess(String resourceEntity, Access access, Object... instances) throws NoPermissionException;

    boolean canAccessMappedResource(String mappedResource, Access access);

    void setAuthorityProvider(IAuthorityProvider provider);

    void setAuthorityContext(IProtectedAccessContext accessContext);
}
