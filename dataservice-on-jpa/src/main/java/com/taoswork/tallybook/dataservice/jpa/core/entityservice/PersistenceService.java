package com.taoswork.tallybook.dataservice.jpa.core.entityservice;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao Yuan on 2015/9/28.
 */

//Aspected by OpenEntityManagerAop.java
public interface PersistenceService {
    public static final String COMPONENT_NAME = "PersistenceService";

    @Transactional
    <T extends Persistable> PersistableResult<T> create(Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> PersistableResult<T> read(Class<T> projectedEntityType, Object key, ExternalReference externalReference) throws ServiceException;

    @Transactional
    <T extends Persistable> PersistableResult<T> update(Class<T> projectedEntityType, T entity) throws ServiceException;

    @Transactional
    <T extends Persistable> boolean delete(Class<T> projectedEntityType, Object key) throws ServiceException;

    /**
     * @param entity
     * @param id:    OPTIONAL, used if param entity doesn't contains id field
     * @param <T>
     * @return
     * @throws ServiceException
     */
//    @Transactional
//    <T extends Persistable> Void delete(Entity entity, String id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> query(
            Class<T> projectedEntityType, CriteriaTransferObject query,
            ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;
}
