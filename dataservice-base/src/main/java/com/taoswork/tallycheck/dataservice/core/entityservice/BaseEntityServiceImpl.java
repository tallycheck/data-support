package com.taoswork.tallycheck.dataservice.core.entityservice;

import com.taoswork.tallycheck.authority.core.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.security.ISecurityVerifier;
import com.taoswork.tallycheck.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallycheck.dataservice.service.EntityMetaAccess;
import com.taoswork.tallycheck.dataservice.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class BaseEntityServiceImpl<Pb extends Persistable>
        implements IEntityService<Pb> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityServiceImpl.class);

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    @Resource(name = SecurityVerifierAgent.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    protected final ConvertUtilsBean2 convertUtils;

    public BaseEntityServiceImpl() {
        this.convertUtils = new ConvertUtilsBean2();
    }

    protected void entityAccessExceptionHandler(Exception e) throws ServiceException {
        throw ServiceException.treatAsServiceException(e);
    }

    @Override
    public <T extends Pb> PersistableResult<T> read(T entity) throws ServiceException {
        Class directClz = entity.getClass();
        Object key =  getEntityId(directClz, entity);
        return read(directClz, key);
    }

    @Override
    public <T extends Pb> PersistableResult<T> read(Class<T> entityClz, Object key) throws ServiceException {
        return read(entityClz, key, null);
    }

    @Override
    public <T extends Pb> boolean delete(final T entity) throws ServiceException {
        Class directClz = entity.getClass();
        Object key =  getEntityId(directClz, entity);
        return delete(directClz, key);
    }

    @Override
    public <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException {
        return this.query(entityClz, query, null, CopyLevel.List);
    }

    @Override
    public <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException {
        return this.query(entityClz, query, null, copyLevel);
    }

    @Override
    public <T extends Pb> T queryOne(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException {
        CriteriaQueryResult<T> result = this.query(entityClz, query, copyLevel);
        List<T> list = result.getEntityCollection();
        if(list != null && !list.isEmpty()){
            T entity = list.get(0);
            return entity;
        }
        return null;
    }

    @Override
    public <T extends Pb> T straightRead(Class<T> entityClz, Object key) throws ServiceException {
        PersistableResult<T> result = read(entityClz, key, new ExternalReference());
        return result.getValue();
    }

    @Override
    public <T extends Pb> Access getAuthorizeAccess(Class<T> entityType, Access mask) {
        if (mask == null) mask = Access.Crudq;
        Access access = securityVerifier.getAllPossibleAccess(entityType.getName(), mask);
        return access;
    }

    @Override
    public <T extends Pb> PersistableResult<T> makeDissociatedPersistable(Class<T> entityClz) throws ServiceException {
        Class rootable = entityMetaAccess.getRootInstantiableEntityType(entityClz);
        try {
            PersistableResult<T> persistableResult = new PersistableResult<T>();
            T entity = (T) rootable.newInstance();
            persistableResult.setValue(entity);

            Class clz = entity.getClass();
            IClassMeta classMeta = entityMetaAccess.getClassMeta(clz, false);
            Field idField = classMeta.getIdField();
            if (idField != null) {
                Object id = idField.get(entity);
                persistableResult.setIdKey(idField.getName())
                        .setIdValue((id == null) ? null : id.toString());
            }
            Field nameField = classMeta.getNameField();
            if (nameField != null) {
                String name = (String) nameField.get(entity);
                persistableResult.setName(name);
            }

            return persistableResult;
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public <T extends Pb> IClassMeta inspectMeta(Class<T> entityType, boolean withHierarchy) {
        return entityMetaAccess.getClassMeta(entityType, withHierarchy);
    }

    @Override
    public <T extends Pb> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale) {
        return entityMetaAccess.getEntityInfo(entityType, withHierarchy, locale, infoType);
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

    protected Object keyTypeAdjuest(Class entityClz, Object key){
        IClassMeta cm = this.entityMetaAccess.getClassMeta(entityClz, false);
        String idFieldName = cm.getIdFieldName();
        IFieldMeta fm = cm.getFieldMeta(idFieldName);
        Class targetClass = fm.getFieldClass();
        if(key.getClass().equals(targetClass)){
            return key;
        }
        return convertUtils.convert(key, targetClass);
    }
}
