package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;

/**
 * Created by gaoyuan on 7/2/16.
 */
public interface IAuthorityProvider {

    ResProtection getProtection(ProtectionScope scope, String resourceTypeName);

    IKPermission getPermission(ProtectionScope scope, String resourceTypeName, String userId);

    ResProtectionWithPermission getProtectionWithPermission(ProtectionScope scope, String resourceTypeName, String userId);

    boolean canAccessMappedResource(ProtectionScope scope, String virtualResource, Access requiredAccess, String userId);
}
