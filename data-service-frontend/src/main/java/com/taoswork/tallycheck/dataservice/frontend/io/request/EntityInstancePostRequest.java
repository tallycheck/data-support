package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.dataio.FormEntity;
import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallycheck.general.extension.utils.CloneUtility;

import java.net.URI;

public abstract class EntityInstancePostRequest extends EntityRequest {

    private FormEntity entity;

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam, URI fullUri, FormEntity entity) {
        super(entityTypeParam, fullUri);
        this.entity = CloneUtility.makeClone(entity);
    }

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        this(entityTypeParam, fullUri, null);
    }

    public EntityInstancePostRequest setEntity(FormEntity entity) {
        this.entity = CloneUtility.makeClone(entity);
        return this;
    }

    public FormEntity getEntity() {
        return entity;
    }
}
