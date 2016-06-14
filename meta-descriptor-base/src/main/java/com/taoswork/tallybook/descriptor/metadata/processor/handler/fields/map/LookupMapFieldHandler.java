package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.map;

import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.LookupMapFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class LookupMapFieldHandler extends BaseFieldHandler {
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        Class collectionType = field.getType();
        if (!Map.class.isAssignableFrom(collectionType)) {
            return false;
        }
        IFacet facet = metaMediate.getFacet(LookupMapFieldMeta.Facet.class);
        if (facet != null) {
            return true;
        }
        MapField cf = field.getDeclaredAnnotation(MapField.class);
        if (cf != null && MapMode.Lookup.equals(cf.mode())) {
            return true;
        }
        return false;
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        LookupMapFieldMeta.Facet facet = LookupMapFieldMeta.Facet.byAnnotation(field);
        if (facet == null) {
            facet = metaMediate.getFacet(LookupMapFieldMeta.Facet.class);
        }
        if (facet != null) {
            IFieldMetaSeed seed = new LookupMapFieldMeta.Seed(facet);
            metaMediate.setMetaSeed(seed);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.FAILED;
    }
}
