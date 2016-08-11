package com.taoswork.tallycheck.dataservice.io.request;

import com.taoswork.tallycheck.dataservice.InfoType;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class InfoRequest extends Request {
    public InfoRequest() {
    }

    public InfoRequest(String type, Locale locale) {
        super(type, locale);
    }

    public InfoRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public InfoRequest(Request req) {
        super(req);
    }

    public boolean withHierarchy;
    public InfoType infoType;
}
