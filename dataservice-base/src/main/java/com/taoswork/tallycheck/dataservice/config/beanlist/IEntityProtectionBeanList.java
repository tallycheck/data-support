package com.taoswork.tallycheck.dataservice.config.beanlist;

import com.taoswork.tallycheck.dataservice.service.EntityValidationService;
import com.taoswork.tallycheck.dataservice.service.EntityValueGateService;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IEntityProtectionBeanList {
    EntityValidationService entityValidatorService();

    EntityValueGateService entityValueGateService();

}
