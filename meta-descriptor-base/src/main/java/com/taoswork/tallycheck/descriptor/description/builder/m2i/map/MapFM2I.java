package com.taoswork.tallycheck.descriptor.description.builder.m2i.map;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.map.MapFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public abstract class MapFM2I<Fm extends MapFieldMeta> extends FM2I<Fm> {

    @Override
    public IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced) {
        if(fieldMeta instanceof MapFieldMeta){
            final MapFieldMeta typedFieldMeta = (MapFieldMeta) fieldMeta;
            final Class referencingCollectionEntryCls = typedFieldMeta.getPresentationClass();
            if (referencingCollectionEntryCls != null) {
                collectionTypeReferenced.add(referencingCollectionEntryCls);
            }
        } else {
            throw new MetadataException("Unproper FM2I registered");
        }
        return super.createInfo(topMeta, prefix, fieldMeta, collectionTypeReferenced);
    }
}
