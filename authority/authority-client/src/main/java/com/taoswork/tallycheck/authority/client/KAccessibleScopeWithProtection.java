package com.taoswork.tallycheck.authority.client;

import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class KAccessibleScopeWithProtection {
    public KAccessibleScope accessibleScope;
    public IKProtection protection;

    public KAccessibleScopeWithProtection(KAccessibleScope accessibleScope, IKProtection protection) {
        this.accessibleScope = accessibleScope;
        this.protection = protection;
    }
}
