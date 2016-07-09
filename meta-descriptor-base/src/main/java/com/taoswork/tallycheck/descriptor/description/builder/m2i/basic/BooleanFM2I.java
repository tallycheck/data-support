package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.BooleanFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.BooleanFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class BooleanFM2I extends FM2I<BooleanFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return BooleanFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     BooleanFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        BooleanFieldInfo booleanFieldInfo = new BooleanFieldInfo(name, friendlyName, editable, ((BooleanFieldMeta) fieldMeta).getMode());
        return booleanFieldInfo;
    }
}
