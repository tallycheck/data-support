package com.taoswork.tallycheck.datasolution.service;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import com.taoswork.tallycheck.info.IEntityInfo;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IEntityService<Pb extends Persistable> {
    public static final String COMPONENT_NAME = "IEntityService";

    <T extends Pb> PersistableResult<T> create(Operator operator, SecurityAccessor accessor, T entity) throws ServiceException;

    <T extends Pb> PersistableResult<T> read(Operator operator, SecurityAccessor accessor, Class<T> entityClz, Object key, ExternalReference externalReference) throws ServiceException;

    <T extends Pb> PersistableResult<T> update(Operator operator, SecurityAccessor accessor, T entity) throws ServiceException;

    <T extends Pb> boolean delete(Operator operator, SecurityAccessor accessor, Class<T> entityClz, Object key) throws ServiceException;

    <T extends Pb> CriteriaQueryResult<T> query(SecurityAccessor accessor, Class<T> entityClz, CriteriaTransferObject query, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

    <T extends Pb> CriteriaQueryResult<T> queryIds(SecurityAccessor accessor, Class<T> entityClz, Collection<String> ids, ExternalReference externalReference, CopyLevel copyLevel) throws ServiceException;

    <T extends Pb> IClassMeta inspectMeta(Class<T> entityType, boolean withHierarchy);

    <T extends Pb> PersistableResult<T> makeDissociatedPersistable(Class<T> entityClz) throws ServiceException;

    <T extends Pb> IEntityInfo describe(Class<T> entityType, boolean withHierarchy, EntityInfoType infoType, Locale locale);

    <T extends Pb> Access getAuthorizeAccess(SecurityAccessor accessor, Class<T> entityType, Access mask);
}