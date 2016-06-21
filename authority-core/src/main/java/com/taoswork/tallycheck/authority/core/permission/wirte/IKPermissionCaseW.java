package com.taoswork.tallycheck.authority.core.permission.wirte;

import com.taoswork.tallycheck.authority.core.permission.IKPermissionCase;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IKPermissionCaseW extends IKPermissionCase {

    IKPermissionCaseW merge(IKPermissionCase that);
}
