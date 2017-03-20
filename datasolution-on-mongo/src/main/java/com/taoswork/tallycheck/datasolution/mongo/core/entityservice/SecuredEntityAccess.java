package com.taoswork.tallycheck.datasolution.mongo.core.entityservice;

import com.mongodb.WriteResult;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.core.SecuredCrudqAccessor;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.config.beanlist.IMongoBeanList;
import com.taoswork.tallycheck.datasolution.mongo.core.query.MongoQueryTranslator;
import com.taoswork.tallycheck.datasolution.mongo.core.query.impl.MongoQueryTranslatorImpl;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.general.solution.exception.UnexpectedException;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public class SecuredEntityAccess extends SecuredCrudqAccessor {
    public static final String COMPONENT_NAME = "SecuredEntityAccess";

    @Resource(name = IMongoBeanList.DATASTORE_BEAN_NAME)
    private AdvancedDatastore datastore;

    private MongoQueryTranslator queryTranslator = new MongoQueryTranslatorImpl();

    @Override
    protected <T extends Persistable> T doCreate(Class<T> projectedEntityType, T entity) {
        String collection = getCollectionName(projectedEntityType);
        Key<T> key = datastore.save(collection, entity);
        return entity;
    }

    @Override
    protected <T extends Persistable> T doRead(Class<T> projectedEntityType, Object key) {
        String collection = getCollectionName(projectedEntityType);
        return datastore.get(collection, projectedEntityType, key);
    }

    @Override
    protected <T extends Persistable> T doUpdate(Class<T> projectedEntityType, T entity) {
        String collection = getCollectionName(projectedEntityType);
        Key<T> key = datastore.save(collection, entity);
        return entity;
    }

    @Override
    protected <T extends Persistable> boolean doDelete(Class<T> projectedEntityType, T entity) {
        String collection = getCollectionName(projectedEntityType);
        Object idVal = null;
        if (entity instanceof AbstractDocument) {
            idVal = ((AbstractDocument) entity).getId();
        } else {
            try {
                IClassMeta cm = this.entityMetaAccess.getClassMeta(projectedEntityType, false);
                Field idField = cm.getIdField();
                if (idField != null) {
                    idVal = idField.get(entity);
                }
            } catch (IllegalAccessException e) {
                throw new UnexpectedException(e);
            }
        }
        if (idVal == null) {
            throw new UnexpectedException();
        }
        WriteResult wr = datastore.delete(collection, projectedEntityType, idVal);
        int n = wr.getN();
        if (n > 1)
            throw new UnexpectedException();
        return n == 1;
    }

    @Override
    protected <T extends Persistable> CriteriaQueryResult<T> doQuery(Class<T> projectedEntityType, CriteriaTransferObject cto) {
        String collection = getCollectionName(projectedEntityType);
        IClassMeta classTreeMeta = entityMetaAccess.getClassTreeMeta(projectedEntityType);
        Query<T> q = queryTranslator.constructListQuery(datastore, collection, projectedEntityType, classTreeMeta, cto);
        Query<T> qc = queryTranslator.constructCountQuery(datastore, collection, projectedEntityType, classTreeMeta, cto);

        List<T> resultList = q.asList();
        long count = qc.countAll();

        CriteriaQueryResult<T> queryResult = new CriteriaQueryResult<T>(projectedEntityType.getName());
        queryResult.setEntityCollection(resultList).setTotalCount(count).setStartIndex(cto.getFirstResult());

        return queryResult;
    }

    private String getCollectionName(Class projectedEntityType) {
        return datastore.getCollection(projectedEntityType).getName();
    }
}