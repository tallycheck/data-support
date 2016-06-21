package com.taoswork.tallycheck.dataservice.server.manage;

import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallycheck.dataservice.service.IEntityService;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public interface DataServiceManager {
    public static final String COMPONENT_NAME = "DataServiceManager";

    void doInitialize();

    DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService);

    DataServiceManager buildingAnnounceFinishing();

    ManagedEntityCatalog getInterfaceEntityEntry(String entityClz);

    String getEntityInterfaceName(String resourceName);

    String getEntityResourceName(String entityClz);

    IDataService getDataServiceByServiceName(String serviceName);

    IDataService getDataService(String entityClz);

    IEntityService getDynamicEntityService(String entityClz);

    InputEntityTranslator getEntityTranslator();

//    FrontEndEntityService getFrontEndDynamicEntityService(String entityClz);
}
