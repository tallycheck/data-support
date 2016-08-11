package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class ReadRequest extends Request {
    public ReadRequest() {
    }

    public ReadRequest(String type, Locale locale) {
        super(type, locale);
    }

    public ReadRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public ReadRequest(Request req) {
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
