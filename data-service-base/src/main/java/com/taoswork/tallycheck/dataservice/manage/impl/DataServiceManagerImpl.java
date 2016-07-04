package com.taoswork.tallycheck.dataservice.manage.impl;

import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.manage.DataServiceManager;
import com.taoswork.tallycheck.dataservice.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class DataServiceManagerImpl implements DataServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceManager.class);

    // DataSolutionMark name to DataSolutionMark
    private final Map<String, IDataService> dataServiceMap = new HashMap<String, IDataService>();

    private final Map<String, EntityType> entityTypeNameToCatalogMap = new HashMap<String, EntityType>();

    private final Map<String, String> entityResNameToTypeName = new HashMap<String, String>();

    public DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService) {
        dataServiceMap.put(dataServiceBeanName, dataService);
        for (final EntityType entityType : dataService.getEntityTypes()) {
            String typeName = entityType.getEntityInterfaceName();
            if (entityTypeNameToCatalogMap.containsKey(typeName)) {
                LOGGER.error("ManagedEntityCatalog with name '{}' already exist, over-writing", typeName);
            }
            entityTypeNameToCatalogMap.put(typeName, entityType);
        }
        return this;
    }

    public DataServiceManager buildingAnnounceFinishing() {
        for (Map.Entry<String, EntityType> entryEntry : entityTypeNameToCatalogMap.entrySet()) {
            String interfaceName = entryEntry.getKey();
            EntityType managedEntityCatalog = entryEntry.getValue();

            String resourceName = managedEntityCatalog.getResourceName();
            if (entityResNameToTypeName.containsKey(resourceName)) {
                LOGGER.error("ResourceName '{}' for interface '{}' already used.", resourceName, interfaceName);
            }
            entityResNameToTypeName.put(resourceName, interfaceName);
        }

        return this;
    }

    @Override
    public EntityType getInterfaceEntityEntry(String entityType) {
        return entityTypeNameToCatalogMap.get(entityType);
    }

    @Override
    public String getEntityInterfaceName(String resourceName) {
        return entityResNameToTypeName.get(resourceName);
    }

    @Override
    public String getEntityResourceName(String entityType) {
        EntityType managedEntityCatalog = getInterfaceEntityEntry(entityType);
        if (null != managedEntityCatalog) {
            return managedEntityCatalog.getResourceName();
        } else {
            return "";
        }
    }

    @Override
    public IDataService getDataServiceByServiceName(String serviceName) {
        return dataServiceMap.get(serviceName);
    }

    @Override
    public IDataService getDataService(String entityType) {
        EntityType managedEntityCatalog = getInterfaceEntityEntry(entityType);
        if (null != managedEntityCatalog) {
            return dataServiceMap.get(managedEntityCatalog.getDataServiceName());
        } else {
            return null;
        }
    }
}
