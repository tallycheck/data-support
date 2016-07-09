package com.taoswork.tallycheck.descriptor.description.builder.m2i.map;

import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.map.LookupMapFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typedcollection.LookupCollectionFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class LookupMapFM2I extends MapFM2I<LookupMapFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, LookupMapFieldMeta fieldMeta, String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        LookupCollectionFieldInfo fieldInfo = new LookupCollectionFieldInfo(name, friendlyName, editable,
                referencingCollectionEntryCls.getName());
        return fieldInfo;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return LookupMapFieldMeta.class;
    }
}
