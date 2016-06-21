package com.taoswork.tallycheck.authority.solution.mockup.service;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class EasyEntityServiceAccess {
    private final IEntityService entityService;

    public EasyEntityServiceAccess(IEntityService entityService) {
        this.entityService = entityService;
    }

    public <T extends Persistable> boolean create(T entity){
        try {
            PersistableResult result = entityService.create(entity);
            if(result == null)
                return false;
            return null != result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public <T extends Persistable> T read(Class<T> ceilingType, Object key){
        try {
            PersistableResult<T> result = entityService.read(ceilingType, key);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public <T extends Persistable> T update(T entity){
        try {
            PersistableResult<T> result = entityService.update(entity);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public <T extends Persistable> boolean delete(T entity){
        try {
            boolean result = entityService.delete( entity);
            return result;
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public <T extends Persistable> T queryOne(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel){
        List<T> r = query(entityClz, query, copyLevel);
        if(r != null && !r.isEmpty()){
            return r.get(0);
        }
        return null;
    }

    public <T extends Persistable> List<T> query(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel){
        try {
            CriteriaQueryResult<T> result = entityService.query(entityClz, query, copyLevel);
            if(result != null){
                return result.getEntityCollection();
            }
            return null;
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
