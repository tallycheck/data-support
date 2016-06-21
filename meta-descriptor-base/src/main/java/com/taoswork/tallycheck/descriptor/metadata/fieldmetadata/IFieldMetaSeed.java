package com.taoswork.tallycheck.descriptor.metadata.fieldmetadata;

import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/24.
 */
public interface IFieldMetaSeed extends Serializable {
    IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo);
}
