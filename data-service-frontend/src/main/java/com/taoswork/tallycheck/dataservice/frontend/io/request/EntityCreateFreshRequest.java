package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;

import java.net.URI;
import java.util.Locale;

public class EntityCreateFreshRequest extends EntityRequest {
    public EntityCreateFreshRequest(EntityTypeParameter entityTypeParam,
                                    URI fullUri, Locale locale) {
        super(entityTypeParam, fullUri, locale);
    }
}
