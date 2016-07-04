package com.taoswork.tallycheck.datasolution.jpa.core.entityservice.impl;

import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.core.entityservice.BaseEntityServiceImpl;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.PersistenceService;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.reference.ExternalReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/22.
 */

//@Secured
//@Transactional
public final class JpaEntityServiceImpl
        extends BaseEntityServiceImpl<Persistable>
        implements JpaEntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaEntityServiceImpl.class);

    @Resource(name = PersistenceService.COMPONENT_NAME)
    protected PersistenceService persistenceService;

    public JpaEntityServiceImpl() {
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(final T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);

            return persistenceService.create(projectedEntityType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjust(projectedEntityType, key);
            return persistenceService.read(projectedEntityType, key, externalReference);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(final T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);
            return persistenceService.update(projectedEntityType, entity);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends Persistable> boolean delete(Class<T> entityClz, Object key) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjust(projectedEntityType, key);
            return persistenceService.delete(projectedEntityType, key);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return false;
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(
            Class<T> entityClz, CriteriaTransferObject query,
            ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            return persistenceService.query(projectedEntityType, query, externalReference, copyLevel);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
            return null;
        }
    }

    protected Class<?> getProjectedEntityType(Class<?> entityClz) {
        Class<?> entityRootClz = this.entityMetaAccess.getRootInstantiableEntityType(entityClz);
        return entityRootClz;
    }



}
