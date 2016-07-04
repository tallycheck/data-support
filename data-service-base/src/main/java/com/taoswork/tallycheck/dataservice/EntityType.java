package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntityHelper;

/**
 * Created by gaoyuan on 6/30/16.
 */
public class EntityType {
    private final String dataServiceName;
    private final String resourceName;
    private final String entityInterfaceName;

    public EntityType(String dataServiceName, Class<?> entityInterface) {
        this.dataServiceName = dataServiceName;
        this.entityInterfaceName = entityInterface.getName();
        this.resourceName = PersistEntityHelper.getEntityName(entityInterface);
    }

    protected EntityType(String dataServiceName, String resource, String entityInterfaceName) {
        this.dataServiceName = dataServiceName;
        this.resourceName = resource;
        this.entityInterfaceName = entityInterfaceName;
    }

    public EntityType(EntityType other) {
        this.dataServiceName = other.dataServiceName;
        this.resourceName = other.resourceName;
        this.entityInterfaceName = other.entityInterfaceName;
    }

    public String getDataServiceName() {
        return dataServiceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getEntityInterfaceName() {
        return entityInterfaceName;
    }


}
