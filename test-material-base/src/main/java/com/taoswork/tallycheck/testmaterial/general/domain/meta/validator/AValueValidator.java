package com.taoswork.tallycheck.testmaterial.general.domain.meta.validator;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class AValueValidator implements IEntityValidator {
    @Override
    public boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
        return true;
    }
}
