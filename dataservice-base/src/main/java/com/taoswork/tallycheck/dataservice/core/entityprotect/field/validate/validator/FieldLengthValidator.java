package com.taoswork.tallycheck.dataservice.core.entityprotect.field.validate.validator;

import com.taoswork.tallycheck.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.dataservice.core.entityprotect.field.validate.BaseTypedFieldValidator;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.StringFieldMeta;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class FieldLengthValidator extends BaseTypedFieldValidator<String> {
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
        if (fieldMeta instanceof StringFieldMeta) {
            int maxLength = ((StringFieldMeta) fieldMeta).getLength();
            int length = fieldValue.length();
            if (length > maxLength) {
                return new ValidationError("validation.error.field.length",
                        new Object[]{maxLength, length});
            }
        }
        return null;
    }
}
