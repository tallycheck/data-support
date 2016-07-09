package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.DateFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.DateFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class DateFM2I extends FM2I<DateFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return DateFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     DateFieldMeta fieldMeta, String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        DateFieldInfo dateFieldInfo = new DateFieldInfo(name, friendlyName, editable,
                fieldMeta.getMode(), fieldMeta.getCellMode());
        return dateFieldInfo;
    }
}
