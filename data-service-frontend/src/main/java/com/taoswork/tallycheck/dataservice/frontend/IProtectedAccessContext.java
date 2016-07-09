package com.taoswork.tallycheck.dataservice.frontend;

import com.taoswork.tallycheck.authority.core.ProtectionScope;

/**
 * Created by gaoyuan on 7/5/16.
 */
public interface IProtectedAccessContext {
    String getCurrentUserId();

    ProtectionScope getCurrentProtectionScope();

}