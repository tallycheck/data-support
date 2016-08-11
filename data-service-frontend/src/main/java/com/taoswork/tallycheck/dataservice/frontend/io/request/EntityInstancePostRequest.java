package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.dataio.FormEntity;
import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallycheck.general.extension.utils.CloneUtility;

import java.net.URI;
import java.util.Locale;

public abstract class EntityInstancePostRequest extends EntityRequest {

    private FormEntity entity;

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam,
                                     URI fullUri, FormEntity entity, Locale locale) {
        super(entityTypeParam, fullUri, locale);
        this.entity = CloneUtility.makeClone(entity);
    }

    public EntityInstancePostRequest(EntityTypeParameter entityTypeParam,
                                     URI fullUri, Locale locale) {
        this(entityTypeParam, fullUri, null, locale);
    }

    public EntityInstancePostRequest setEntity(FormEntity entity) {
        this.entity = CloneUtility.makeClone(entity);
        return this;
    }

    public FormEntity getEntity() {
        return entity;
    }
}
