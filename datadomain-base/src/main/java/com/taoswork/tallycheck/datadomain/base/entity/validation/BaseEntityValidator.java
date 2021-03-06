package com.taoswork.tallycheck.datadomain.base.entity.validation;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;

public abstract class BaseEntityValidator<T>
        implements IEntityValidator {

    @Override
    public final boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
        return doValidate((T) entity, validationErrors);
    }

    protected abstract boolean doValidate(T entity, EntityValidationErrors validationErrors);
}
