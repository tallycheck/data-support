package com.taoswork.tallycheck.datasolution.jpa.core.entityservice;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/9/28.
 */

//Aspected by OpenEntityManagerAop.java
public interface PersistenceService {
    public static final String COMPONENT_NAME = "PersistenceService";

    @Transactional
    <T extends Persistable> PersistableResult<T> create(SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(SecurityAccessor accessor, Class<T> projectedEntityType, Object key, ExternalReference externalReference) throws ServiceException;

    @Transactional
    <T extends Persistable> PersistableResult<T> update(SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException;

    @Transactional
    <T extends Persistable> boolean delete(SecurityAccessor accessor, Class<T> projectedEntityType, Object key) throws ServiceException;

    /**
     * @param entity
     * @param id:    OPTIONAL, used if param entity doesn't contains id field
     * @param <T>
     * @return
     * @throws ServiceException
     */
//    @Transactional
//    <T extends Persistable> Void delete(Entity entity, String id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(SecurityAccessor accessor,
                                                         Class<T> projectedEntityType, CriteriaTransferObject query,
                                                         ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> queryIds(SecurityAccessor accessor,
                                                         Class<T> projectedEntityType, Collection<String> ids,
                                                         ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;
}
