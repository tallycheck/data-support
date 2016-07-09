package com.taoswork.tallycheck.dataservice.io.request;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyuan on 7/9/16.
 */
public class RequestEntity implements Serializable {
    private Class<? extends Persistable> type;
    private final Map<String, String> props;

    public RequestEntity(){
        this(new HashMap<String, String>());
    }

    public RequestEntity(Map<String, String> props){
        this.props = props;
    }

    public RequestEntity(Class<? extends Persistable> type, Map<String, String> props) {
        this.type = type;
        this.props = props;
    }

    public Class<? extends Persistable> getType() {
        return type;
    }

    public RequestEntity setType(Class<? extends Persistable> type) {
        this.type = type;
        return this;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public RequestEntity setProperty(String key, String value){
        this.props.put(key, value);
        return this;
    }}
