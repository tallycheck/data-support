package com.taoswork.tallycheck.authority.core.permission;

import com.taoswork.tallycheck.authority.atom.Access;

import java.io.Serializable;

/**
 * IKPermissionCase, user owned, directly or indirectly.
 * <p>
 * And always has a corresponding IKProtectionCase in NativeCoreProtectionCenter (mapped by case's code)
 */
public interface IKPermissionCase extends Serializable {

    String getCode();

    Access getAccess();

    IKPermissionCase clone();
}
