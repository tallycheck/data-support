package com.taoswork.tallybook.dataservice.mongo.core.entityservice.impl;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.entityservice.BaseEntityServiceImpl;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallybook.dataservice.mongo.core.convertors.ObjectIdConverter;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.SecuredEntityAccess;
import com.taoswork.tallybook.dataservice.service.EntityCopierService;
import com.taoswork.tallybook.dataservice.service.EntityValidationService;
import com.taoswork.tallybook.dataservice.service.EntityValueGateService;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public class MongoEntityServiceImpl
        extends BaseEntityServiceImpl<PersistableDocument>
        implements MongoEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoEntityServiceImpl.class);

    @Resource(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    private AdvancedDatastore datastore;

    @Resource(name = EntityValidationService.COMPONENT_NAME)
    protected EntityValidationService entityValidationService;

    @Resource(name = EntityValueGateService.COMPONENT_NAME)
    protected EntityValueGateService entityValueGateService;

    @Resource(name = EntityCopierService.COMPONENT_NAME)
    protected EntityCopierService entityCopierService;

    @Resource(name = SecuredEntityAccess.COMPONENT_NAME)
    protected SecuredEntityAccess securedEntityAccess;

    public MongoEntityServiceImpl() {
        convertUtils.register(new ObjectIdConverter(), ObjectId.class);
    }

    @Override
    public AdvancedDatastore getAdvancedDatastore() {
        return this.datastore;
    }

    @Override
    public Datastore getDatastore() {
        return this.datastore;
    }

    @Override
    public <T extends PersistableDocument> PersistableResult<T> create(T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class ceilingType = getProjectedEntityType(directClz);

            T result = (T) securedEntityAccess.securedCreate(ceilingType, entity);
            return securedEntityAccess.makePersistableResult(result);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjuest(projectedEntityType, key);
            T result = (T) securedEntityAccess.securedRead(projectedEntityType, key);

            CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
            T safeResult = this.entityCopierService.makeSafeCopy(copierContext, result, CopyLevel.Swap);

            return securedEntityAccess.makePersistableResult(safeResult);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> PersistableResult<T> update(T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);
            T result = (T) securedEntityAccess.securedUpdate(projectedEntityType, entity);
            return securedEntityAccess.makePersistableResult(result);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> boolean delete(Class<T> entityClz, Object key) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjuest(projectedEntityType, key);
            return securedEntityAccess.securedDelete(projectedEntityType, key);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return false;
    }

//    @Override
//    public <T extends PersistableDocument> PersistableResult<T> create(Entity entity) throws ServiceException {
//        try {
//            T instance = (T) converter.convert(entity, null);
//            Class ceilingType = getCeilingType(entity);
//            return this.create(ceilingType, instance);
//        } catch (Exception e) {
//            entityAccessExceptionHandler(e);
//        }
//        return null;
//    }
//
//    @Override
//    public <T extends PersistableDocument> PersistableResult<T> update(Entity entity) throws ServiceException {
//        try {
//            T instance = (T) converter.convert(entity, null);
//            Class ceilingType = getCeilingType(entity);
//            return this.update(ceilingType, instance);
//        } catch (Exception e) {
//            entityAccessExceptionHandler(e);
//        }
//        return null;
//    }
//
//    @Override
//    public <T extends PersistableDocument> boolean delete(Entity entity, String id) throws ServiceException {
//        try {
//            Class ceilingType = getCeilingType(entity);
//            T instance = (T) converter.convert(entity, id);
//            return this.delete(ceilingType, instance);
//        } catch (Exception e) {
//            entityAccessExceptionHandler(e);
//        }
//        return false;
//    }

    @Override
    public <T extends PersistableDocument> CriteriaQueryResult<T> query(
            Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            if (query == null)
                query = new CriteriaTransferObject();
            CriteriaQueryResult<T> criteriaQueryResult = securedEntityAccess.securedQuery(projectedEntityType, query);
            CriteriaQueryResult<T> safeResult = new CriteriaQueryResult<T>(criteriaQueryResult.getEntityType())
                    .setStartIndex(criteriaQueryResult.getStartIndex())
                    .setTotalCount(criteriaQueryResult.getTotalCount());
            List<T> records = criteriaQueryResult.getEntityCollection();
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
