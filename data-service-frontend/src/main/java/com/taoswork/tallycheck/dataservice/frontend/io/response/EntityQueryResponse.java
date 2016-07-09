package com.taoswork.tallycheck.dataservice.frontend.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityQueryResult;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryResponse extends EntityResponse {
    private final String queryUri;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    EntityQueryResult entities;

    public EntityQueryResponse(String queryUri) {
        this.queryUri = queryUri;
    }

    public String getQueryUri() {
        return queryUri;
    }

    public EntityQueryResult getEntities() {
        return entities;
    }

    public EntityQueryResponse setEntities(EntityQueryResult entities) {
        this.entities = entities;
        return this;
    }

    @Override
    public String getAction() {
        return EntityAction.QUERY.getType();
    }
}