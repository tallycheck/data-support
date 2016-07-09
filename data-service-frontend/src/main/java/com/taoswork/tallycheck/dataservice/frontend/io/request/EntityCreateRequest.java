package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.dataio.FormEntity;
import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;

import java.net.URI;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityCreateRequest extends EntityInstancePostRequest {
    public EntityCreateRequest(EntityTypeParameter entityTypeParam, URI fullUri, FormEntity entity) {
        super(entityTypeParam, fullUri, entity);
    }
}
