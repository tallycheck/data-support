package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.PaleFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.PaleFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class PaleFM2I extends FM2I<PaleFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return PaleFieldMeta.class;
    }

    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     PaleFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        PaleFieldInfo stringFieldInfo = new PaleFieldInfo(name, friendlyName, editable);
        return stringFieldInfo;
    }
}
