package com.taoswork.tallycheck.datasolution.service.impl;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.EntityValidationErrorCodeException;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.core.entityprotect.field.validate.validator.*;
import com.taoswork.tallycheck.datasolution.core.entityprotect.validate.EntityValueValidator;
import com.taoswork.tallycheck.datasolution.core.entityprotect.validate.EntityValueValidatorManager;
import com.taoswork.tallycheck.datasolution.core.entityprotect.validate.EntityValueValidatorOnFields;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.EntityValidationService;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationServiceImpl implements EntityValidationService {
    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    private final EntityValueValidator entityValidatorOnFields;
    private final EntityValueValidator entityValidatorManager;

    public EntityValidationServiceImpl() {
        EntityValueValidatorOnFields validatorOnFields = new EntityValueValidatorOnFields();
        validatorOnFields
                .addHandler(new FieldRequiredValidator())
                .addHandler(new FieldLengthValidator())
                .addHandler(new EmailFieldValidator())
                .addHandler(new PhoneFieldValidator())
                .addHandler(new ForeignKeyFieldValidator());

        entityValidatorOnFields = validatorOnFields;
        entityValidatorManager = new EntityValueValidatorManager();
    }

    @Override
    public void validate(PersistableResult persistableResult) throws ServiceException {
        Persistable entity = persistableResult.getValue();
        Class entityType = entity.getClass();
        IClassMeta classMeta = entityMetaAccess.getClassMeta(entityType, false);

        try {
            EntityValidationErrors entityErrors = new EntityValidationErrors();

            entityValidatorOnFields.validate(entity, classMeta, entityErrors);
            entityValidatorManager.validate(entity, classMeta, entityErrors);

            if (!entityErrors.isValid()) {
                throw new EntityValidationErrorCodeException(persistableResult, entityErrors);
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(e);
        }

    }

}
