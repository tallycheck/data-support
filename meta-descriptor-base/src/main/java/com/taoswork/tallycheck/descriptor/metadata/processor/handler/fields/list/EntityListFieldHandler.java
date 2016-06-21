package com.taoswork.tallycheck.descriptor.metadata.processor.handler.fields.list;

import com.taoswork.tallycheck.datadomain.base.entity.CollectionField;
import com.taoswork.tallycheck.datadomain.base.entity.CollectionMode;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.EntityListFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFacet;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class EntityListFieldHandler extends BaseFieldHandler {
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        Class collectionType = field.getType();
        if (!Collection.class.isAssignableFrom(collectionType)) {
            return false;
        }
        IFacet facet = metaMediate.getFacet(EntityListFieldMeta.Facet.class);
        if(facet != null){
            return true;
        }
        CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
        if(cf != null && CollectionMode.Entity.equals(cf.mode())){
            return true;
        }
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        EntityListFieldMeta.Facet facet = EntityListFieldMeta.Facet.byAnnotation(field);
        if(facet == null){
            facet = metaMediate.getFacet(EntityListFieldMeta.Facet.class);
        }
        if(facet != null){
            IFieldMetaSeed seed = new EntityListFieldMeta.Seed(facet);
            metaMediate.setMetaSeed(seed);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.FAILED;
    }
}
