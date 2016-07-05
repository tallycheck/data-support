package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class DeleteRequest extends Request {
    public DeleteRequest(String type) {
        super(type);
    }

    public DeleteRequest(Class type) {
        super(type);
    }

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
