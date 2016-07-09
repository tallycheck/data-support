package com.taoswork.tallycheck.info.descriptor.field;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.info.descriptor.base.NamedOrderedInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface IFieldInfo extends NamedOrderedInfo {
    int getVisibility();

    boolean isEditable();

    boolean isRequired();

    FieldType getFieldType();

    boolean isFormVisible();

    boolean ignored();
}
