package com.taoswork.tallycheck.dataservice.frontend;

import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.dataservice.operator.Operator;

/**
 * Created by gaoyuan on 7/5/16.
 */
public interface IProtectedAccessContext {
    boolean isAdministrator();

    String getCurrentBu();

    String getCurrentPersonId();

    String getCurrentEmployeeId();

    ProtectionScope getCurrentProtectionScope();
}