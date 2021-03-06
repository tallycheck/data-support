package com.taoswork.tallycheck.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.Embedded;
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
