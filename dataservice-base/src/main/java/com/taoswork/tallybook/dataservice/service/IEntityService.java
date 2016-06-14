package com.taoswork.tallybook.dataservice.service;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IEntityService<Pb extends Persistable> {
    public static final String COMPONENT_NAME = "IEntityService";

    <T extends Pb> PersistableResult<T> create(T entity) throws ServiceException;

    <T extends Pb> PersistableResult<T> read(T entity) throws ServiceException;

    <T extends Pb> PersistableResult<T> read(Class<T> entityClz, Object key) throws ServiceException;

    <T extends Pb> PersistableResult<T> read(Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Pb> PersistableResult<T> update(T entity) throws ServiceException;

//    <T extends Pb> PersistableResult<T> update(String property, T entity) throws ServiceException;

    <T extends Pb> boolean delete(T entity) throws ServiceException;

    <T extends Pb> boolean delete(Class<T> entityClz, Object key) throws ServiceException;



    <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

    <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException;

    <T extends Pb> CriteriaQueryResult<T> query(Class<T> entityClz, CriteriaTransferObject query) throws ServiceException;

    <T extends Pb> T queryOne(Class<T> entityClz, CriteriaTransferObject query, CopyLevel copyLevel) throws ServiceException;

    <T extends Pb> IClassMeta inspectMeta(Class<T> entityType, boolean withHierarchy);

    <T extends Pb> T straightRead(Class<T> entityClz, Object key) throws ServiceException;

    <T extends Pb> PersistableResult<T> makeDissociatedPersistable(Class<T> entityClz) throws ServiceException;

    <T extends Pb> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale);

    <T extends Pb> Access getAuthorizeAccess(Class<T> entityType, Access mask);
}
