package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.authority.core.ProtectionScope;

/**
 * Created by gaoyuan on 7/5/16.
 */
public interface IProtectedAccessContext {
    String getCurrentUserId();

    ProtectionScope getCurrentProtectionScope();

}