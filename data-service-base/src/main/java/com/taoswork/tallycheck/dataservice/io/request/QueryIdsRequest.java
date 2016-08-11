package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class QueryIdsRequest extends Request {
    public QueryIdsRequest() {
    }

    public QueryIdsRequest(String type, Locale locale) {
        super(type, locale);
    }

    public QueryIdsRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public QueryIdsRequest(Request req) {
        super(req);
    }

    public Collection<String> ids;
}
