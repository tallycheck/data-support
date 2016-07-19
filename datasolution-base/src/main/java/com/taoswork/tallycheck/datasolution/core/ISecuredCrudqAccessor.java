package com.taoswork.tallycheck.datasolution.core;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public interface ISecuredCrudqAccessor {
    <T extends Persistable> T securedCreate(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> T securedRead(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> T securedUpdate(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> boolean securedDelete(Operator operator, SecurityAccessor accessor, Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> securedQuery(SecurityAccessor accessor, Class<T> projectedEntityType, CriteriaTransferObject query) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> securedQueryIds(SecurityAccessor accessor, Class<T> projectedEntityType, Collection<String> ids) throws ServiceException;
}
