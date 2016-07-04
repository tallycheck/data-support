package com.taoswork.tallycheck.authority.provider.onmongo;

import com.taoswork.tallycheck.authority.domain.ProtectionSpace;
import com.taoswork.tallycheck.authority.domain.resource.Protection;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class AuthoritySolutionDomain {
    public static Class[] domainEntities(){
        return new Class<?>[]{
                ProtectionSpace.class,  //feature
                Protection.class,  //feature + tenant
//                UserAuthority.class,//feature + tenant + userid
//                GroupAuthority.class,
        };
    }
}
