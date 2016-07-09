package com.taoswork.tallycheck.dataservice.io.response;

import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class QueryResponse extends Response {
    public ExternalReference references = new ExternalReference();
    public CriteriaQueryResult result;
}
