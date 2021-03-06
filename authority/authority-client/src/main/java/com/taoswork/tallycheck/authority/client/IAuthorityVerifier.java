package com.taoswork.tallycheck.authority.client;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.ProtectionScope;

/**
 * Created by gaoyuan on 7/2/16.
 */
public interface IAuthorityVerifier {
    /**
     * Get all the possible access for the specified resource.
     * @param auth
     * @param resource
     * @return
     */
    Access getAllPossibleAccess(ProtectionScope scope, String userId, String resourceTypeName, Access mask);

    /**
     * Check if auth can access the resource type
     */
    boolean canAccess(ProtectionScope scope, String userId, String resourceTypeName, Access requiredAccess);

    /**
     * check if auth can access the resource instance
     * steps:
     *      1. work out KCaseFitting
     *      2. work out the merged access according to KCaseFitting
     *      3. check if merged access qualified
     */
    boolean canAccess(ProtectionScope scope, String userId, String resourceTypeName, Access requiredAccess, Object... instances);

    /**
     * A shortcut for calcAccessibleScope(IKPermission entityPermission, Access access);
     */
    KAccessibleScopeWithProtection calcAccessibleScope(ProtectionScope scope, String userId, String resourceTypeName, Access access);

    /**
     * Check if auth can access the virtual resource type
     */
    boolean canAccessMappedResource(ProtectionScope scope, String userId, String virtualResource, Access requiredAccess);
}