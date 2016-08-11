package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class DeleteRequest extends Request {
    public DeleteRequest() {
    }

    public DeleteRequest(String type, Locale locale) {
        super(type, locale);
    }

    public DeleteRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public DeleteRequest(Request req) {
        super(req);
    }

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
