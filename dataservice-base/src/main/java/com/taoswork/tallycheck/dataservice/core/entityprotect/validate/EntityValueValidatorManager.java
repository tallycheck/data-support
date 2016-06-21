package com.taoswork.tallycheck.dataservice.core.entityprotect.validate;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.EntityValidatorPool;
import com.taoswork.tallycheck.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

import java.util.Collection;

public class EntityValueValidatorManager extends EntityValidatorPool implements EntityValueValidator {

    @Override
    public void validate(Persistable entity, IClassMeta classMeta, EntityValidationErrors entityValidationErrors) {
        Collection<String> validatorNames = classMeta.getReadonlyValidators();
        for (String validatorName : validatorNames) {
            IEntityValidator validator = getValidator(validatorName);
            if (validator != null) {
                validator.validate(entity, entityValidationErrors);
            }
        }
    }

}
