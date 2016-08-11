package com.taoswork.tallycheck.dataservice.frontend.io.request;

import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.CollectionEntryTypeParameter;
import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;

import java.net.URI;
import java.util.Locale;

public class CollectionEntryCreateFreshRequest extends EntityRequest {
    public final CollectionEntryTypeParameter collectionEntryTypeParameter;

    public CollectionEntryCreateFreshRequest(EntityTypeParameter entityTypeParam,
                                             URI fullUri,
                                             CollectionEntryTypeParameter collectionEntryTypeParameter,
                                             Locale locale) {
        super(entityTypeParam, fullUri, locale);
        this.collectionEntryTypeParameter = collectionEntryTypeParameter;
    }

    public Class getEntryPresentationClass() {
        return collectionEntryTypeParameter.getType();
    }
}
