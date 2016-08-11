package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;

import java.net.URI;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public class EntityInfoRequest extends EntityRequest {
    public EntityInfoRequest(EntityTypeParameter entityTypeParam,
                             URI fullUri, Locale locale) {
        super(entityTypeParam, fullUri, locale);
    }
}
