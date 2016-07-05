package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermission;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionMapping;

/**
 * Created by gaoyuan on 7/5/16.
 */
public class AllPassAuthorityProvider extends BaseAuthorityProvider {
    private final KProtectionMapping protectionMapping = new KProtectionMapping();

    @Override
    protected IKProtectionMapping getProtectionMapping(ProtectionScope scope) {
        return protectionMapping;
    }

    @Override
    protected IKPermission doGetPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        KPermission permission = new KPermission(resourceTypeName);
        permission.setMasterAccess(new Access(0xFFFFFFFF, 0xFFFFFFFF));
        return permission;
    }

    @Override
    protected ResProtection doGetProtection(ProtectionScope scope, String resourceTypeName) {
        ResProtection resProtection = new ResProtection();
        resProtection.resource = resourceTypeName;
        resProtection.masterControlled = false;
        resProtection.protectionMode = ProtectionMode.FitAny;
        return resProtection;
    }

}