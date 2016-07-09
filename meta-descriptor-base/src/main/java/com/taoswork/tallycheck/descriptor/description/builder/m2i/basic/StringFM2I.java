package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.StringFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.StringFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class StringFM2I extends FM2I<StringFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return StringFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     StringFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        StringFieldInfo stringFieldInfo = new StringFieldInfo(name, friendlyName, editable, fieldMeta.getLength());
        return stringFieldInfo;
    }
}
