package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.dataio.FormEntity;
import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;

import java.net.URI;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDeleteRequest extends EntityInstancePostRequest {
    protected String id;

    public EntityDeleteRequest(EntityTypeParameter entityTypeParam,
                               URI fullUri, FormEntity entity, Locale locale) {
        super(entityTypeParam, fullUri, entity, locale);
    }

    public EntityDeleteRequest(EntityTypeParameter entityTypeParam,
                               URI fullUri, Locale locale) {
        super(entityTypeParam, fullUri, locale);
    }

    public String getId() {
        return id;
    }

    public EntityDeleteRequest setId(String id) {
        this.id = id;
        return this;
    }
}
