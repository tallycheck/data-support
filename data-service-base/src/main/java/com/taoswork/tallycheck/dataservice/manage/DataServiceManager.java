package com.taoswork.tallycheck.dataservice.manage;

import com.taoswork.tallycheck.dataservice.EntityType;
import com.taoswork.tallycheck.dataservice.IDataService;

/**
 * Created by gaoyuan on 6/29/16.
 */
public interface DataServiceManager {

    EntityType getInterfaceEntityEntry(String entityClz);

    String getEntityInterfaceName(String resourceName);

    String getEntityResourceName(String entityClz);

    IDataService getDataServiceByServiceName(String serviceName);

    IDataService getDataService(String entityClz);


}
