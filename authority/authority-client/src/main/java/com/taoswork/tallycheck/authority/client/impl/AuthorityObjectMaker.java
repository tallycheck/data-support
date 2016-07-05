package com.taoswork.tallycheck.authority.client.impl;

import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.client.filter.IFilter;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.core.resource.IKProtectionCase;
import com.taoswork.tallycheck.authority.core.resource.impl.KProtection;
import com.taoswork.tallycheck.authority.provider.ResProtection;
import com.taoswork.tallycheck.authority.provider.ResProtectionCase;

import java.util.Collection;

/**
 * Created by gaoyuan on 7/3/16.
 */
final class AuthorityObjectMaker {
    public static IKProtection convert(EntityFilterManager fm, ResProtection sr){
        String resource = sr.resource;
        KProtection protection = new KProtection(resource);
        protection.setMasterControlled(sr.masterControlled);
        protection.setProtectionMode(sr.protectionMode);
        Collection<ResProtectionCase> cases = sr.cases;
        if(cases != null){
            for(ResProtectionCase _case : cases){
                IKProtectionCase icase = makeRcProtectionCase(fm, resource, _case);
                if(icase != null){
                    protection.addCase(icase);
                }
            }
        }
        return protection;
    }

    public static IKProtectionCase makeRcProtectionCase(EntityFilterManager fm, String resource, ResProtectionCase _case){
        ResourceProtectionCase rpc = new ResourceProtectionCase(_case.code);
        IFilter filter = fm.getFilter(resource, _case.filterType, _case.filterParameter);
        rpc.setFilter(filter);
        return rpc;
    }

}
