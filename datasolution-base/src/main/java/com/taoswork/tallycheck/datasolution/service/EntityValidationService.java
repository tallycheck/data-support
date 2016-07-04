package com.taoswork.tallycheck.datasolution.service;

import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public interface EntityValidationService {
    public final static String COMPONENT_NAME = "EntityValidationService";

    void validate(PersistableResult entity) throws ServiceException;
}
