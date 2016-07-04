package com.taoswork.tallycheck.authority.provider.permission.authorities;

import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.provider.permission.IKAuthority;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface ISimpleKAuthority extends IKAuthority {

    ISimpleKAuthority addPermission(IKPermission permissions);

    ISimpleKAuthority addPermissions(IKPermission... permissions);

    ISimpleKAuthority merge(ISimpleKAuthority that);

    ISimpleKAuthority clone();
}
