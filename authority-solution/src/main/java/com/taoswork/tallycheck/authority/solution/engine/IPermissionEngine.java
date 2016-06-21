package com.taoswork.tallycheck.authority.solution.engine;

import com.taoswork.tallycheck.authority.core.permission.IKAuthority;
import com.taoswork.tallycheck.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallycheck.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallycheck.authority.core.verifier.IKAccessVerifier;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public interface IPermissionEngine {
    IKProtectionMapping getProtectionMapping(String protectionSpace);

    IKProtectionCenter getProtectionCenter(String protectionSpace, String tenantId);

    IKAccessVerifier getAccessVerifier(String protectionSpace, String tenantId);

    IKAuthority getAuthority(String protectionSpace, String tenantId, String userId);

}
