package com.taosworks.tallycheck.descriptor.mongo.metadata.processor.handler;

import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;
import org.mongodb.morphia.annotations.Embedded;

import java.lang.reflect.Field;

class _EmbeddedAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
        Embedded embeddedAnnotation = field.getDeclaredAnnotation(Embedded.class);
        if (null != embeddedAnnotation) {
            EmbeddedFieldMeta.Facet facet = new EmbeddedFieldMeta.Facet(field.getDeclaringClass());
            metaMediate.pushFacet(facet);
            return ProcessResult.HANDLED;
        } else {
            return ProcessResult.INAPPLICABLE;
        }
    }
}
