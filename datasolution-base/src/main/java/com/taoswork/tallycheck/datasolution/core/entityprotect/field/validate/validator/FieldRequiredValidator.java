package com.taoswork.tallycheck.datasolution.core.entityprotect.field.validate.validator;

import com.taoswork.tallycheck.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datasolution.core.entityprotect.field.validate.BaseTypedFieldValidator;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class FieldRequiredValidator extends BaseTypedFieldValidator<String> {

    @Override
    public FieldType supportedFieldType() {
        return null;
    }

    @Override
    public Class supportedFieldClass() {
        return String.class;
    }

    @Override
    public ValidationError doValidate(IFieldMeta fieldMeta, String fieldValue) {
        if (fieldMeta.isRequired() && StringUtils.isEmpty(fieldValue)) {
            return new ValidationError("validation.error.field.required");
        }
        return null;
    }
}
