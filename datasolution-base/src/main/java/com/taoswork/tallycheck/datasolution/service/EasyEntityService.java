package com.taoswork.tallycheck.datasolution.service;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by gaoyuan on 7/9/16.
 */
public class EasyEntityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyEntityService.class);

    public EasyEntityService(EntityMetaAccess entityMetaAccess, IEntityService entityService) {
        this.entityMetaAccess = entityMetaAccess;
        this.entityService = entityService;
    }

    public EasyEntityService(IDataSolution solution) {
        this.entityMetaAccess = solution.getService(EntityMetaAccess.COMPONENT_NAME);
        this.entityService = solution.getService(IEntityService.COMPONENT_NAME);
    }

    protected final EntityMetaAccess entityMetaAccess;
    protected final IEntityService entityService;

    public <T extends Persistable> boolean create(SecurityAccessor accessor, T entity){
        try {
            PersistableResult result = entityService.create(accessor, entity);
            if(result == null)
                return false;
            return null != result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public PersistableResult read(SecurityAccessor accessor, Persistable entity) throws ServiceException {
        Class directClz = entity.getClass();
        Object key = getEntityId(directClz, entity);
        return read(accessor, directClz, key);
    }

    public PersistableResult read(SecurityAccessor accessor, Class<? extends Persistable> entityClz, Object key) throws ServiceException {
        return entityService.read(accessor, entityClz, key, null);
    }

    public <T extends Persistable> T read(SecurityAccessor accessor, Class<T> ceilingType, Object key, ExternalReference externalReference){
        try {
            PersistableResult<T> result = entityService.read(accessor, ceilingType, key, externalReference);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public <T extends Persistable> T update(SecurityAccessor accessor, T entity){
        try {
            PersistableResult<T> result = entityService.update(accessor, entity);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public boolean delete(SecurityAccessor accessor, final Persistable entity) throws ServiceException {
        Class directClz = entity.getClass();
        Object key = getEntityId(directClz, entity);
        return entityService.delete(accessor, directClz, key);
    }

    public CriteriaQueryResult query(SecurityAccessor accessor, Class<? extends Persistable> entityClz, CriteriaTransferObject query) throws ServiceException {
        return entityService.query(accessor, entityClz, query, null, CopyLevel.List);
    }

    public CriteriaQueryResult query(SecurityAccessor accessor, Class<? extends Persistable> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException {
        return entityService.query(accessor, entityClz, query, null, copyLevel);
    }

    public <T extends Persistable> T  queryOne(SecurityAccessor accessor, Class<? extends T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException {
        CriteriaQueryResult result = this.query(accessor, entityClz, query, copyLevel);
        List list = result.getEntityCollection();
        if (list != null && !list.isEmpty()) {
            T entity = (T)list.get(0);
            return entity;
        }
        return null;
    }


    protected <T extends Persistable> Object getEntityId(Class projectedEntityType, T entity) throws ServiceException {
        try {
            IClassMeta rootClzMeta = entityMetaAccess.getClassMeta(projectedEntityType, false);
            Field idField = rootClzMeta.getIdField();

            Object id = idField.get(entity);
            return id;
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

}
