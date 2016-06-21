package com.taoswork.tallycheck.dataservice.server.io.request;

import com.taoswork.tallycheck.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;

import java.net.URI;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityUpdateRequest extends EntityInstancePostRequest {
    public EntityUpdateRequest(EntityTypeParameter entityTypeParam, URI fullUri, Entity entity) {
        super(entityTypeParam, fullUri, entity);
    }
}
