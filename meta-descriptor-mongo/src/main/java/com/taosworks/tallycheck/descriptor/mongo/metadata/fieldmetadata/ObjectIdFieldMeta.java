package com.taosworks.tallycheck.descriptor.mongo.metadata.fieldmetadata;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ObjectIdFieldMeta extends BasePrimitiveFieldMeta {
    public ObjectIdFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.ID;
    }

    public static class Seed implements IFieldMetaSeed {
        public Seed() {
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new ObjectIdFieldMeta(bfmo);
        }
    }

    @Override
    public Object getValueFromString(String valStr) {
        if(StringUtils.isEmpty(valStr)){
            return null;
        }
        return new ObjectId(valStr);
    }
}
