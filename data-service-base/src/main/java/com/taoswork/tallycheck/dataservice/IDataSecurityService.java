package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.atom.Access;

/**
 * Created by gaoyuan on 7/2/16.
 */
public interface IDataSecurityService {
    Access getAllPossibleAccess(String userId, String resourceEntity, Access mask);

    boolean canAccess(String userId, String resourceEntity, Access access);


}
