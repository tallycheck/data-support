package com.taoswork.tallycheck.dataservice.core.entityprotect.validate;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValueValidator {
    void validate(Persistable entity, IClassMeta classMeta, EntityValidationErrors entityErrors) throws IllegalAccessException;
}
