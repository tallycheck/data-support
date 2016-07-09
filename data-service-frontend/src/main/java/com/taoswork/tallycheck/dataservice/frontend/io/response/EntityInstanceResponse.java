package com.taoswork.tallycheck.dataservice.frontend.io.response;

import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityInstanceResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public abstract class EntityInstanceResponse extends EntityResponse {

    EntityInstanceResult entity;

    public EntityInstanceResult getEntity() {
        return entity;
    }

    public EntityInstanceResponse setEntity(EntityInstanceResult entity) {
        this.entity = entity;
        return this;
    }
}
