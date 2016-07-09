package com.taoswork.tallycheck.descriptor.dataio.in;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Entity {
    private Class<? extends Persistable> type;
    private final Map<String, String> props;

    public Entity(){
        this(new HashMap<String, String>());
    }

    public Entity(Map<String, String> props){
        this.props = props;
    }

    public Entity(Class<? extends Persistable> type, Map<String, String> props) {
        this.type = type;
        this.props = props;
    }

    public Class<? extends Persistable> getType() {
        return type;
    }

    public Entity setType(Class<? extends Persistable> type) {
        this.type = type;
        return this;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public Entity setProperty(String key, String value){
        this.props.put(key, value);
        return this;
    }
}
