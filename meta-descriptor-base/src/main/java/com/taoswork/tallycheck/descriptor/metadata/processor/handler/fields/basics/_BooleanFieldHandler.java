package com.taoswork.tallycheck.descriptor.metadata.processor.handler.fields.basics;

import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationBoolean;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.BooleanFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.BaseFieldHandler;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
class _BooleanFieldHandler extends BaseFieldHandler {
    private boolean fits(Field field) {
        if (Boolean.class.equals(field.getType())) {
            return true;
        } else if (boolean.class.equals(field.getType())) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        return fits(field);
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        IFieldMetaSeed booleanFieldSeed = null;
        PresentationBoolean presentationBoolean = field.getDeclaredAnnotation(PresentationBoolean.class);
        if (presentationBoolean != null) {
            booleanFieldSeed = new BooleanFieldMeta.Seed(presentationBoolean.mode());
        } else {
            booleanFieldSeed = new BooleanFieldMeta.Seed();
        }
        metaMediate.setMetaSeed(booleanFieldSeed);
        return ProcessResult.HANDLED;
    }
}
