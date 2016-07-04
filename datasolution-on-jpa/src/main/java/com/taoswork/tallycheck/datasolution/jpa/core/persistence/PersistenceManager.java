package com.taoswork.tallycheck.datasolution.jpa.core.persistence;

import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.reference.ExternalReference;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T extends Persistable> PersistableResult<T> create(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> boolean delete(Class<T> ceilingType, Object key) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

}
