package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import com.taoswork.tallycheck.dataservice.operator.Operator;

import java.util.Collection;

/**
 * Created by gaoyuan on 6/29/16.
 */
public interface IDataService {

    String getName();

    Collection<EntityType> getEntityTypes();

    NewInstanceResponse newInstance(NewInstanceRequest request) throws ServiceException;

    CreateResponse create(Operator operator, SecurityAccessor accessor, CreateRequest request) throws ServiceException;

    ReadResponse read(Operator operator, SecurityAccessor accessor, ReadRequest request) throws ServiceException;

    UpdateResponse update(Operator operator, SecurityAccessor accessor, UpdateRequest request) throws ServiceException;

    UpdateFieldResponse update(Operator operator, SecurityAccessor accessor, UpdateFieldRequest request);

    DeleteResponse delete(Operator operator, SecurityAccessor accessor, DeleteRequest request) throws ServiceException;

    QueryResponse query(SecurityAccessor accessor, QueryRequest request) throws ServiceException;

    QueryResponse query(SecurityAccessor accessor, QueryIdsRequest request) throws ServiceException;

    InfoResponse info(InfoRequest request);

    Access getAuthorizeAccess(SecurityAccessor accessor, String entityType, Access mask);
}