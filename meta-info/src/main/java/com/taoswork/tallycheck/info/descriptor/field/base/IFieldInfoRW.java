package com.taoswork.tallycheck.info.descriptor.field.base;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.info.descriptor.base.NamedOrdered;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface IFieldInfoRW extends NamedOrdered, IFieldInfo {
    void setVisibility(int visibility);

    void setRequired(boolean required);

    void setFieldType(FieldType fieldType);

    void setIgnored(boolean ignored);
}
