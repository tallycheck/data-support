package com.taoswork.tallycheck.authority.domain;

import com.taoswork.tallycheck.authority.domain.resource.Protection;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class AuthorityDomain {
    public static Class[] domainEntities(){
        return new Class<?>[]{
                ProtectionSpec.class,  //feature
                Protection.class,  //feature + tenant
//                UserAuthority.class,//feature + tenant + userid
//                GroupAuthority.class,
        };
    }
}
