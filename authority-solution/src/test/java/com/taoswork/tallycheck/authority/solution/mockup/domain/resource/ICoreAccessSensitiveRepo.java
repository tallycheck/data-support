package com.taoswork.tallycheck.authority.solution.mockup.domain.resource;

import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.core.resource.KAccessibleScope;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/5.
 */
public interface ICoreAccessSensitiveRepo<T> {
    List<T> query(KAccessibleScope accessibleScope, IKProtection resourceProtection);
}
