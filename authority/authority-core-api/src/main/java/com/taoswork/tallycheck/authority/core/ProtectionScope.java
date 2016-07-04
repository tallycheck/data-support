package com.taoswork.tallycheck.authority.core;

/**
 * Created by gaoyuan on 7/2/16.
 */
public final class ProtectionScope {
    public String protectionSpace;
    public String tenantId;

    public ProtectionScope(String protectionSpace, String tenantId) {
        this.protectionSpace = protectionSpace;
        this.tenantId = tenantId;
    }

    public ProtectionScope() {
    }
}
