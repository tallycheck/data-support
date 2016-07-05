package com.taoswork.tallycheck.datasolution.jpa.core.entityservice.impl;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.PersistenceService;
import com.taoswork.tallycheck.datasolution.jpa.core.metaaccess.JpaEntityMetaAccess;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.IPersistentMethod;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManagerInvoker;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.reference.ExternalReference;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

//Aspected by OpenEntityManagerAop.java
public class PersistenceServiceImpl implements PersistenceService {

    @Resource(name = JpaEntityMetaAccess.COMPONENT_NAME)
    protected JpaEntityMetaAccess entityMetaAccess;

    /**
     * Multi-thread support for io with EntityManager
     */
    @Resource(name = PersistenceManagerInvoker.COMPONENT_NAME)
    protected PersistenceManagerInvoker persistenceManagerIsolatedInvoker;
//
//    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
//    protected ISecurityVerifier securityVerifier;

    @Override
    @Transactional
    public <T extends Persistable> PersistableResult<T> create(final Class<T> projectedEntityType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.create(projectedEntityType, entity);
            }
        });
    }

    @Override
    public <T extends Persistable> PersistableResult<T> read(final Class<T> projectedEntityType, final Object key, final ExternalReference externalReference) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.read(projectedEntityType, key, externalReference);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> PersistableResult<T> update(final Class<T> projectedEntityType, final T entity) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<PersistableResult<T>, ServiceException>() {
            @Override
            public PersistableResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.update(projectedEntityType, entity);
            }
        });
    }

    @Override
    @Transactional
    public <T extends Persistable> boolean delete(final Class<T> projectedEntityType, final Object key) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<Boolean, ServiceException>() {
            @Override
            public Boolean execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.delete(projectedEntityType, key);
            }
        });
    }

    @Override
    public <T extends Persistable> CriteriaQueryResult<T> query(
            final Class<T> projectedEntityType, final CriteriaTransferObject query,
            final ExternalReference externalReference, final CopyLevel copyLevel) throws ServiceException {
        return persistenceManagerIsolatedInvoker.operation(new IPersistentMethod<CriteriaQueryResult<T>, ServiceException>() {
            @Override
            public CriteriaQueryResult<T> execute(PersistenceManager persistenceManager) throws ServiceException {
                return persistenceManager.query(projectedEntityType, query, externalReference, copyLevel);
            }
        });
    }

}
