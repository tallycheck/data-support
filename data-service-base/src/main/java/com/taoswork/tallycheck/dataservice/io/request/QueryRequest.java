package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class QueryRequest extends Request {
    public QueryRequest(String type) {
        super(type);
    }

    public QueryRequest(Class type) {
        super(type);
    }
}
