package com.taoswork.tallycheck.datasolution.config.beanlist;

import com.taoswork.tallycheck.datasolution.service.EntityValidationService;
import com.taoswork.tallycheck.datasolution.service.EntityValueGateService;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IEntityProtectionBeanList {
    EntityValidationService entityValidatorService();

    EntityValueGateService entityValueGateService();

}
