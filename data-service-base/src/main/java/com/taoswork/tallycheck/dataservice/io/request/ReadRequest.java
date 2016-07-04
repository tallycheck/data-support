package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class ReadRequest extends Request {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
