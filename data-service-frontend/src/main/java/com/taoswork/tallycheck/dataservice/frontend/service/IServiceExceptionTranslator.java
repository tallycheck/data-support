package com.taoswork.tallycheck.dataservice.frontend.service;

import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityErrors;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public interface IServiceExceptionTranslator {
    void translate(ServiceException serviceException, EntityErrors errors);
}
