package com.taoswork.tallycheck.authority.provider.onmongo.common.client;

import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/5.
 */
public interface ICoreAccessSensitiveRepo<T> {
    List<T> query(KAccessibleScope accessibleScope, IKProtection resourceProtection);
}
