package com.taoswork.tallycheck.descriptor.description.builder.m2i.list;

import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.LookupListFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typedcollection.LookupCollectionFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class LookupListFM2I extends ListFM2I<LookupListFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, LookupListFieldMeta fieldMeta,
                                     String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        IFieldInfoRW result = null;
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        final String referencingCollectionEntryClsName = (referencingCollectionEntryCls != null) ? referencingCollectionEntryCls.getName() : "";

        result = new LookupCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
        return result;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return LookupListFieldMeta.class;
    }
}
