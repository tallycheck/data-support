package com.taoswork.tallycheck.dataservice.exception;

import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallycheck.dataservice.PersistableResult;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationErrorCodeException extends ServiceException {
    private final PersistableResult entity;
    private EntityValidationErrors entityValidationError;

    public EntityValidationErrorCodeException(PersistableResult entity, EntityValidationErrors entityValidationError) {
        this.entity = entity;
        this.entityValidationError = entityValidationError;
    }

    public EntityValidationErrors getEntityValidationError() {
        return entityValidationError;
    }

    public void setEntityValidationError(EntityValidationErrors entityValidationError) {
        this.entityValidationError = entityValidationError;
    }

    public PersistableResult getEntity() {
        return entity;
    }
}
