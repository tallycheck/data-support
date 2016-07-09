package com.taoswork.tallycheck.dataservice.io.request;

import java.io.Serializable;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class Request implements Serializable {
    /**
     * Entity type: basically interface/class name
     */
    protected String type;

    public Request(String type) {
        this.type = type;
    }

    public Request(Class type) {
        this.type = type.getName();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setType(Class type) {
        this.type = type.getName();
    }
}
