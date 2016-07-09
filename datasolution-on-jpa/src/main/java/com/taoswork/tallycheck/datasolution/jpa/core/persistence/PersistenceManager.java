package com.taoswork.tallycheck.datasolution.jpa.core.persistence;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public interface PersistenceManager {
    public static final String COMPONENT_NAME = "PersistenceManager";

    <T extends Persistable> PersistableResult<T> create(SecurityAccessor accessor, Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(SecurityAccessor accessor, Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Persistable> PersistableResult<T> update(SecurityAccessor accessor, Class<T> ceilingType, T entity) throws ServiceException;

    <T extends Persistable> boolean delete(SecurityAccessor accessor, Class<T> ceilingType, Object key) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(SecurityAccessor accessor, Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> queryIds(SecurityAccessor accessor, Class<T> entityClz, Collection<String> ids, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;
}
