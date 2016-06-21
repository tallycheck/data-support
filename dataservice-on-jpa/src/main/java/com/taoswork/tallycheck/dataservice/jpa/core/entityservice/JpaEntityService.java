package com.taoswork.tallycheck.dataservice.jpa.core.entityservice;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.service.IEntityService;

/**
 * Entry of data access.
 * <p>
 * JpaEntityService(JpaEntityServiceImpl)
 * ()
 * |
 * |
 * \/
 * PersistenceService(PersistenceServiceImpl)
 * ({@link PersistenceService})
 * (Aspected by OpenEntityManagerAop.java)
 * (@Transactional)
 * (ThreadSafe by PersistenceManagerInvoker)
 * |
 * |
 * \/
 * PersistenceManager(PersistenceManagerImpl)
 * ({@link PersistenceManager})
 * (Security Check)
 * (Value Gate)
 * (Value Validation)
 */
public interface JpaEntityService extends IEntityService<Persistable> {

}