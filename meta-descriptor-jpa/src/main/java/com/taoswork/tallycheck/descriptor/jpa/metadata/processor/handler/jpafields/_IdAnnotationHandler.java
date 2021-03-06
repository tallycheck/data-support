package com.taoswork.tallycheck.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.Id;
import java.lang.reflect.Field;

class _IdAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
        Id idAnnotation = field.getDeclaredAnnotation(Id.class);
        if (null != idAnnotation) {
            bfmo.setId(true);
            PresentationField presentationField = field.getDeclaredAnnotation(PresentationField.class);
            if (presentationField == null) {
                bfmo.setOrder(0);
                bfmo.setVisibility(Visibility.HIDDEN_ALL);
            }
            return ProcessResult.HANDLED;
        } else {
            bfmo.setId(false);
            return ProcessResult.INAPPLICABLE;
        }
    }
}
