package com.taoswork.tallycheck.datasolution.core.entityprotect.valuegate;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface EntityValueGate {
    void store(IClassMeta classMeta, Persistable entity, Persistable oldEntity) throws ServiceException;

    void fetch(IClassMeta classMeta, Persistable entity);
}
