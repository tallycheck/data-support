package com.taoswork.tallycheck.dataservice.io.response;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;

/**
 * Created by gaoyuan on 7/1/16.
 */
public abstract class InstanceResponse extends Response {
    public Persistable result;
    String idKey;
    String idValue;
    String name;

    public Persistable getResult() {
        return result;
    }

    public void setResult(Persistable result) {
        this.result = result;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
