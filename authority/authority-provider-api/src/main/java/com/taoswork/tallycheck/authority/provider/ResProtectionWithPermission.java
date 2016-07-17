package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.core.permission.IKPermission;

import java.io.Serializable;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class ResProtectionWithPermission implements Serializable {
    public ResProtection resProtection;
    public IKPermission permission;

    public ResProtectionWithPermission(ResProtection resProtection, IKPermission permission) {
        this.resProtection = resProtection;
        this.permission = permission;
    }

    public ResProtectionWithPermission() {
    }
}
