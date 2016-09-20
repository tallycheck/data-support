package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class CreateGetRequest extends Request {
    public CreateGetRequest() {
    }

    public CreateGetRequest(String type, Locale locale) {
        super(type, locale);
    }

    public CreateGetRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public CreateGetRequest(Request req) {
        super(req);
    }
}
