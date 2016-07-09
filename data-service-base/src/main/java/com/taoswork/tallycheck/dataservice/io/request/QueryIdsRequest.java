package com.taoswork.tallycheck.dataservice.io.request;

import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;

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

    public CriteriaTransferObject query;
}
