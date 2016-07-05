package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class UpdateFieldRequest extends Request {
    /**
     * field name
     */
    protected String field;

    public UpdateFieldRequest(String type) {
        super(type);
    }

    public UpdateFieldRequest(Class type) {
        super(type);
    }
}
