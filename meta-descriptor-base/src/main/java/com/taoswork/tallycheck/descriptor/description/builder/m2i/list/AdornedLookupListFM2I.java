package com.taoswork.tallycheck.descriptor.description.builder.m2i.list;

import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.AdornedLookupListFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typedcollection.AdornedLookupCollectionFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class AdornedLookupListFM2I extends ListFM2I<AdornedLookupListFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, AdornedLookupListFieldMeta fieldMeta,
                                     String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        IFieldInfoRW result = null;
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        final String referencingCollectionEntryClsName = (referencingCollectionEntryCls != null) ? referencingCollectionEntryCls.getName() : "";

        result = new AdornedLookupCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
        return result;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return AdornedLookupListFieldMeta.class;
    }
}
