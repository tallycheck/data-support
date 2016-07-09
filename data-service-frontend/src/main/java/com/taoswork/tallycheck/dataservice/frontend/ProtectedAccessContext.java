package com.taoswork.tallycheck.dataservice.frontend;

import com.taoswork.tallycheck.authority.core.ProtectionScope;

/**
 * Created by gaoyuan on 7/5/16.
 */
public class ProtectedAccessContext implements IProtectedAccessContext {
    protected String currentUserId;
    protected ProtectionScope protectionScope;

    public ProtectedAccessContext() {
    }

    public ProtectedAccessContext(String currentUserId, ProtectionScope protectionScope) {
        this.currentUserId = currentUserId;
        this.protectionScope = protectionScope;
    }

    @Override
    public String getCurrentUserId() {
        return currentUserId;
    }

    @Override
    public ProtectionScope getCurrentProtectionScope() {
        return protectionScope;
    }
}
