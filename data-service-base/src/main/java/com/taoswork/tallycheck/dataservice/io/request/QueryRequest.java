package com.taoswork.tallycheck.dataservice.io.request;

import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class QueryRequest extends Request {
    public QueryRequest() {
    }

    public QueryRequest(String type, Locale locale) {
        super(type, locale);
    }

    public QueryRequest(Class type, Locale locale) {
        super(type, locale);
    }

    public QueryRequest(Request req) {
        super(req);
    }

    public CriteriaTransferObject query;
}
