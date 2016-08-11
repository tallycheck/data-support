package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class NewInstanceRequest extends Request {
    public NewInstanceRequest() {
    }

    public NewInstanceRequest(String type, Locale locale) {
        super(type, locale);
    }

    public NewInstanceRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public NewInstanceRequest(Request req) {
        super(req);
    }
}
