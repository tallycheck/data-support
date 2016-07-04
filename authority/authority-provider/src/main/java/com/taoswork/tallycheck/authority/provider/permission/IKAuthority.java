package com.taoswork.tallycheck.authority.provider.permission;

import com.taoswork.tallycheck.authority.core.permission.IKPermission;

/**
 * IKAuthority is owner of .IKPermission (of different resource entities)
 * We may treat it as a user, owning permissions
 */
public interface IKAuthority {

    IKPermission getPermission(String resource);
}
