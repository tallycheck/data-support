package com.taoswork.tallycheck.datasolution.core;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public interface ISecuredCrudqAccessor {
    <T extends Persistable> T securedCreate(Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> T securedRead(Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> T securedUpdate(Class<T> projectedEntityType, T entity) throws ServiceException;

    <T extends Persistable> boolean securedDelete(Class<T> projectedEntityType, Object id) throws ServiceException;

    <T extends Persistable> CriteriaQueryResult<T> securedQuery(Class<T> projectedEntityType, CriteriaTransferObject query) throws ServiceException;
}
