package com.taoswork.tallycheck.datasolution.mongo.core.entityservice.impl;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.core.entityservice.BaseEntityServiceImpl;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.core.convertors.ObjectIdConverter;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.SecuredEntityAccess;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.datasolution.service.EntityValidationService;
import com.taoswork.tallycheck.datasolution.service.EntityValueGateService;
import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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
    public <T extends PersistableDocument> PersistableResult<T> create(Operator operator,SecurityAccessor accessor, T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class ceilingType = getProjectedEntityType(directClz);

            T result = (T) securedEntityAccess.securedCreate(operator,accessor, ceilingType, entity);
            return securedEntityAccess.makePersistableResult(result);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> PersistableResult<T> read(Operator operator,SecurityAccessor accessor, Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjust(projectedEntityType, key);
            T result = (T) securedEntityAccess.securedRead(operator,accessor, projectedEntityType, key);

            CopierContext copierContext = new CopierContext(this.entityMetaAccess, externalReference);
            T safeResult = this.entityCopierService.makeSafeCopy(copierContext, result, CopyLevel.Swap);

            return securedEntityAccess.makePersistableResult(safeResult);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> PersistableResult<T> update(Operator operator,SecurityAccessor accessor, T entity) throws ServiceException {
        try {
            Class directClz = entity.getClass();
            Class projectedEntityType = getProjectedEntityType(directClz);
            T result = (T) securedEntityAccess.securedUpdate(operator,accessor, projectedEntityType, entity);
            return securedEntityAccess.makePersistableResult(result);
        } catch (Exception e) {
            entityAccessExceptionHandler(e);
        }
        return null;
    }

    @Override
    public <T extends PersistableDocument> boolean delete(Operator operator,SecurityAccessor accessor, Class<T> entityClz, Object key) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            key = keyTypeAdjust(projectedEntityType, key);
            return securedEntityAccess.securedDelete(operator,accessor, projectedEntityType, key);
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
    public <T extends PersistableDocument> CriteriaQueryResult<T> query(SecurityAccessor accessor,
                                                                        Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            if (query == null)
                query = new CriteriaTransferObject();
            CriteriaQueryResult<T> criteriaQueryResult = securedEntityAccess.securedQuery(accessor, projectedEntityType, query);
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

    @Override
    public <T extends PersistableDocument> CriteriaQueryResult<T> queryIds(SecurityAccessor accessor,
                                                                           Class<T> entityClz, Collection<String> ids, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException {
        try {
            Class projectedEntityType = getProjectedEntityType(entityClz);
            CriteriaQueryResult<T> criteriaQueryResult = securedEntityAccess.securedQueryIds(accessor, projectedEntityType, ids);
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
