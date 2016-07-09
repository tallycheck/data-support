package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Collection;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class QueryIdsRequest extends Request {
    public QueryIdsRequest(String type) {
        super(type);
    }

    public QueryIdsRequest(Class type) {
        super(type);
    }

    public Collection<String> ids;
}
