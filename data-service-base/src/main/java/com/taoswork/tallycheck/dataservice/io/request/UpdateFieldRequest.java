package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class UpdateFieldRequest extends Request {
    /**
     * field name
     */
    protected String field;

    public UpdateFieldRequest() {
    }

    public UpdateFieldRequest(String type, Locale locale) {
        super(type, locale);
    }

    public UpdateFieldRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public UpdateFieldRequest(Request req) {
        super(req);
    }
}
