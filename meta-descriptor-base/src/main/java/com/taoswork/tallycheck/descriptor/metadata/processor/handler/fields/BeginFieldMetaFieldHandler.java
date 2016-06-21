package com.taoswork.tallycheck.descriptor.metadata.processor.handler.fields;

import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;
import com.taoswork.tallycheck.descriptor.metadata.utils.FriendlyNameHelper;

import java.lang.reflect.Field;

public class BeginFieldMetaFieldHandler
        implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
//        bfmo.setField(field);
        bfmo.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Field(field));

        return ProcessResult.PASSING_THROUGH;
    }
}
