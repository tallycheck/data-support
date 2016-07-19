package com.taoswork.tallycheck.datasolution.jpa.core.persistence.impl;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.core.SecuredCrudqAccessor;
import com.taoswork.tallycheck.datasolution.jpa.core.dao.EntityDao;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/16.
 */
public class PersistenceManagerImpl
        extends SecuredCrudqAccessor
        implements PersistenceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManagerImpl.class);

    @Resource(name = EntityDao.COMPONENT_NAME)
    protected EntityDao entityDao;

    @Resource(name = EntityCopierService.COMPONENT_NAME)
    protected EntityCopierService entityCopierService;

    @Override
    protected <T extends Persistable> T doCreate(Class<T> projectedEntityType, T entity) {
        return entityDao.create(entity);
    }

    @Override
    protected <T extends Persistable> T doRead(Class<T> projectedEntityType, Object key) {
        T result = entityDao.read(projectedEntityType, key);
        return result;
    }

    @Override
    protected <T extends Persistable> T doUpdate(Class<T> projectedEntityType, T entity) {
        return entityDao.update(entity);
    }

    @Override
    protected <T extends Persistable> boolean doDelete(Class<T> projectedEntityType, T entity) {
        entityDao.delete(entity);
        return true;
    }

    @Override
    protected <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> projectedEntityType, CriteriaTransferObject query) {
        CriteriaQueryResult<T> result = entityDao.query(projectedEntityType, query);
        return result;
    }

    @Override
    public <T extends Persistable> PersistableResult<T> create(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException {
        T result = securedCreate(operator, accessor, projectedEntityType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, Object key, ExternalReference externalReference) throws ServiceException {
        T result = securedRead(operator, accessor, projectedEntityType, key);

        CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
        T safeResult = this.entityCopierService.makeSafeCopy(copierContext, result, CopyLevel.Read);

        return makePersistableResult(safeResult);
    }

    @Override
    public <T extends Persistable> PersistableResult<T> update(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException {
        T result = securedUpdate(operator, accessor, projectedEntityType, entity);
        return makePersistableResult(result);
    }

    @Override
    public <T extends Persistable> boolean delete(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, Object key) throws ServiceException {
        return securedDelete(operator, accessor, projectedEntityType, key);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(SecurityAccessor accessor,
                                                                Class<T> projectedEntityType, CriteriaTransferObject query,
                                                                ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        if (query == null)
            query = new CriteriaTransferObject();
        CriteriaQueryResult<T> criteriaQueryResult = securedQuery(accessor, projectedEntityType, query);
        return makeSafeCriteriaQueryResult(externalReference, copyLevel, criteriaQueryResult);
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> queryIds(SecurityAccessor accessor,
                                                                   Class<T> projectedEntityType, Collection<String> ids,
                                                                   ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        CriteriaQueryResult<T> criteriaQueryResult = securedQueryIds(accessor, projectedEntityType, ids);
        return makeSafeCriteriaQueryResult(externalReference, copyLevel, criteriaQueryResult);
    }

    private <T extends Persistable> CriteriaQueryResult<T> makeSafeCriteriaQueryResult(ExternalReference externalReference, CopyLevel copyLevel, CriteriaQueryResult<T> criteriaQueryResult) throws ServiceException {
        CriteriaQueryResult<T> safeResult = new CriteriaQueryResult<T>(criteriaQueryResult.getEntityType())
                .setStartIndex(criteriaQueryResult.getStartIndex())
                .setTotalCount(criteriaQueryResult.getTotalCount());
        List<T> records = criteriaQueryResult.getEntityCollection();
        switch (copyLevel) {
            case Read:
            case List:
                break;
            case Swap:
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException();
        }
        if (records != null) {
            List<T> entities = new ArrayList();
            CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
            for (T rec : records) {
                T shallowCopy = this.entityCopierService.makeSafeCopy(copierContext, rec, copyLevel);
                entities.add(shallowCopy);
            }
            safeResult.setEntityCollection(entities);
        }
        return safeResult;
    }
}
