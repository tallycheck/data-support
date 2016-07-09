package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.EnumFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.EnumFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class EnumFM2I extends FM2I<EnumFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return EnumFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     EnumFieldMeta fieldMeta, String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        EnumFieldInfo enumFieldInfo = new EnumFieldInfo(name, friendlyName, editable, ((EnumFieldMeta) fieldMeta).getEnumerationType());
        return enumFieldInfo;
    }
}
