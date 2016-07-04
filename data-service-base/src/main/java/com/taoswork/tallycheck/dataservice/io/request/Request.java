package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class Request {
    /**
     * Entity type: basically interface/class name
     */
    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
