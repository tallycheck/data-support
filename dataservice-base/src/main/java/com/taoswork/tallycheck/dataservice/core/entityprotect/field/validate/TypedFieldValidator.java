package com.taoswork.tallycheck.dataservice.core.entityprotect.field.validate;

import com.taoswork.tallycheck.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallycheck.dataservice.core.entityprotect.field.handler.ITypedFieldHandler;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface TypedFieldValidator extends ITypedFieldHandler {
    ValidationError validate(IFieldMeta fieldMeta, Object fieldValue);
}
