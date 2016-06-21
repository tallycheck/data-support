package com.taoswork.tallycheck.dataservice.server.service;

import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.server.io.response.result.EntityErrors;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public interface IServiceExceptionTranslator {
    void translate(ServiceException serviceException, EntityErrors errors);
}
