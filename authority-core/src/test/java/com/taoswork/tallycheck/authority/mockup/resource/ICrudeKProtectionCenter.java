package com.taoswork.tallycheck.authority.mockup.resource;

import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.core.resource.IKProtectionCenter;

/**
 * ICrudeKProtectionCenter, a manager for all:
 *      IKProtection s
 *      Resource alias
 */
public interface ICrudeKProtectionCenter
        extends IKProtectionCenter {

    /**
     * Add IKProtection object to the manager.
     * @param protection
     * @return
     */
    ICrudeKProtectionCenter registerProtection(IKProtection protection);


}
