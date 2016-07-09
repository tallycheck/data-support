package com.taoswork.tallycheck.descriptor.description.builder.m2i.basic;

import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.typed.ExternalForeignKeyFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class ExternalForeignEntityFM2I extends FM2I<ExternalForeignEntityFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return ExternalForeignEntityFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, ExternalForeignEntityFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        ExternalForeignEntityFieldMeta feFm = fieldMeta;
        ExternalForeignKeyFieldInfo fkFieldInfo = new ExternalForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getDeclareType(), feFm.getTargetType(), feFm.getTheDataFieldName(), feFm.getIdProperty(), feFm.getDisplayProperty());
        return fkFieldInfo;
    }
}
