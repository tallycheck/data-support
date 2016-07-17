package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.core.ProtectionScope;

import java.io.Serializable;

/**
 * Created by gaoyuan on 7/7/16.
 */
public class SecurityAccessor implements Serializable {
    public ProtectionScope protectionScope;
    public String userId;

    public SecurityAccessor(ProtectionScope protectionScope, String userId) {
        this.protectionScope = protectionScope;
        this.userId = userId;
    }

    public SecurityAccessor() {
    }
}
