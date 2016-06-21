package com.taosworks.tallycheck.descriptor.mongo.metadata.processor.field;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.BaseFieldHandler;

import java.lang.reflect.Field;

import com.taosworks.tallycheck.descriptor.mongo.metadata.fieldmetadata.ObjectIdFieldMeta;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ObjectIdFieldMetaHandler extends BaseFieldHandler{
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        BasicFieldMetaObject bfmo = metaMediate.getBasicFieldMetaObject();
        return ObjectId.class.equals(field.getType()) && (!bfmo.getFieldType().equals(FieldType.EXTERNAL_FOREIGN_KEY));
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        metaMediate.setMetaSeed(new ObjectIdFieldMeta.Seed());
        return ProcessResult.HANDLED;
    }
}
