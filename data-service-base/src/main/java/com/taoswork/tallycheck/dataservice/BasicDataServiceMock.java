package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gaoyuan on 7/4/16.
 */
public abstract class BasicDataServiceMock implements IDataService {
    public BasicDataServiceMock(){}

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Collection<EntityType> getEntityTypes() {
        return new ArrayList<EntityType>();
    }

    @Override
    public NewInstanceResponse newInstance(NewInstanceRequest request) throws ServiceException {
        NewInstanceResponse response = new NewInstanceResponse();
        return response;
    }

    @Override
    public CreateResponse create(SecurityAccessor accessor, CreateRequest request) throws ServiceException {
        CreateResponse response = new CreateResponse();
        return response;
    }

    @Override
    public ReadResponse read(SecurityAccessor accessor, ReadRequest request) throws ServiceException {
        ReadResponse response = new ReadResponse();
        return response;
    }

    @Override
    public UpdateResponse update(SecurityAccessor accessor, UpdateRequest request) throws ServiceException {
        UpdateResponse response = new UpdateResponse();
        return response;
    }

    @Override
    public UpdateFieldResponse update(SecurityAccessor accessor, UpdateFieldRequest request) {
        return new UpdateFieldResponse();
    }

    @Override
    public DeleteResponse delete(SecurityAccessor accessor, DeleteRequest request) throws ServiceException {
        DeleteResponse response = new DeleteResponse();
        return response;
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryRequest request) throws ServiceException {
        QueryResponse response = new QueryResponse();
        return response;
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryIdsRequest request) throws ServiceException {
        QueryResponse response = new QueryResponse();
        return response;
    }

    @Override
    public InfoResponse info(InfoRequest request) {
        InfoResponse response = new InfoResponse();
        return response;
    }

    @Override
    public Access getAuthorizeAccess(SecurityAccessor accessor, Class<? extends Persistable> entityType, Access mask) {
        return Access.None;
    }

}
