package com.taoswork.tallycheck.dataservice.frontend.dataio;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.io.request.RequestEntity;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyuan on 6/30/16.
 */
public class FormEntity implements Serializable {
    private Integer timezoneOffset;
    private final Map<String, String> props = new HashMap<String, String>();
    public final static String ENTITY_PROPERTY_NAME = "props";
    private Class<? extends Persistable> type;
    private Class<? extends Persistable> ceilingType;

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }


    public Class<? extends Persistable> getType() {
        return type;
    }

    public FormEntity setType(Class<? extends Persistable> type) {
        this.type = type;
        return this;
    }

    public Class<? extends Persistable> getCeilingType() {
        return ceilingType;
    }

    public FormEntity setCeilingType(Class<? extends Persistable> ceilingType) {
        this.ceilingType = ceilingType;
        return this;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public FormEntity setProperty(String key, String value){
        this.props.put(key, value);
        return this;
    }

    public Entity entity(){
        return new Entity(type, props);
    }

    public RequestEntity requestEntity(){
        return new RequestEntity(type, props);
    }
}
