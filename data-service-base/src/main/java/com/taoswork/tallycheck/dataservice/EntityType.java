package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntityHelper;

import java.io.Serializable;

/**
 * Created by gaoyuan on 6/30/16.
 */
public class EntityType implements Serializable{
    private String dataServiceName;
    private String resource;
    private String interfaceName;

    public EntityType() {
    }

    public EntityType(String dataServiceName, Class<?> entityInterface) {
        this.dataServiceName = dataServiceName;
        this.interfaceName = entityInterface.getName();
        this.resource = PersistEntityHelper.getEntityName(entityInterface);
    }

    protected EntityType(String dataServiceName, String resource, String interfaceName) {
        this.dataServiceName = dataServiceName;
        this.resource = resource;
        this.interfaceName = interfaceName;
    }

    public EntityType(EntityType other) {
        this.dataServiceName = other.dataServiceName;
        this.resource = other.resource;
        this.interfaceName = other.interfaceName;
    }

    public String getDataServiceName() {
        return dataServiceName;
    }

    public String getResource() {
        return resource;
    }

    public String getInterfaceName() {
        return interfaceName;
    }


}
