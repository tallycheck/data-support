package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;

import java.util.Collection;

/**
 * Created by gaoyuan on 6/29/16.
 */
public interface IDataService {

    String getName();

    Collection<EntityType> getEntityTypes();

    NewInstanceResponse newInstance(NewInstanceRequest request) throws ServiceException;

    CreateResponse create(SecurityAccessor accessor, CreateRequest request) throws ServiceException;

    ReadResponse read(SecurityAccessor accessor, ReadRequest request) throws ServiceException;

    UpdateResponse update(SecurityAccessor accessor, UpdateRequest request) throws ServiceException;

    UpdateFieldResponse update(SecurityAccessor accessor, UpdateFieldRequest request);

    DeleteResponse delete(SecurityAccessor accessor, DeleteRequest request) throws ServiceException;

    QueryResponse query(SecurityAccessor accessor, QueryRequest request) throws ServiceException;

    QueryResponse query(SecurityAccessor accessor, QueryIdsRequest request) throws ServiceException;

    InfoResponse info(InfoRequest request);

    Access getAuthorizeAccess(SecurityAccessor accessor, Class< ? extends Persistable> entityType, Access mask);
}