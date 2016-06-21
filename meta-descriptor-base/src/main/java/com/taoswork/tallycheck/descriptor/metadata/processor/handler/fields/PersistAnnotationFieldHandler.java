package com.taoswork.tallycheck.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;

import java.lang.reflect.Field;

public class PersistAnnotationFieldHandler
        implements IFieldHandler {

    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
        bfmo.setFieldType(FieldType.UNKNOWN);
        MutableClassMeta mutableClassMeta = metaMediate.getMutableClassMetadata();
        PersistField persistField = mutableClassMeta.getPersistFieldOverride(field.getName());
        if (persistField == null){
            persistField = field.getDeclaredAnnotation(PersistField.class);
        }

        Class<? extends IFieldGate> fvg = null;
        if (persistField != null) {
            bfmo.setFieldType(persistField.fieldType());
            bfmo.setNameField(FieldType.NAME.equals(persistField.fieldType()));
            bfmo.setRequired(persistField.required());
            bfmo.setLength(persistField.length());

            bfmo.setEditable(persistField.editable());

            fvg = persistField.fieldValueGateOverride();
            if (IFieldGate.class.equals(fvg)) {
                fvg = null;
            }
            boolean skipDefaultFieldValueGate = persistField.skipDefaultFieldValueGate();
            bfmo.setFieldValueGate(fvg, skipDefaultFieldValueGate);

            return ProcessResult.HANDLED;
        } else {
            return ProcessResult.PASSING_THROUGH;
        }
    }
}
