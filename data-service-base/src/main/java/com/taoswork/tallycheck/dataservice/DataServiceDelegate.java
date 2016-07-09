package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;

import java.util.Collection;

/**
 * Created by gaoyuan on 7/5/16.
 */
public class DataServiceDelegate implements IDataService {
    private IDataService dataService;

    public DataServiceDelegate() {
    }

    public void setDataService(IDataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public String getName() {
        return dataService.getName();
    }

    @Override
    public Collection<EntityType> getEntityTypes() {
        return dataService.getEntityTypes();
    }

    @Override
    public NewInstanceResponse newInstance(NewInstanceRequest request)throws ServiceException {
        return dataService.newInstance(request);
    }

    @Override
    public CreateResponse create(SecurityAccessor accessor, CreateRequest request) throws ServiceException{
        return dataService.create(accessor, request);
    }

    @Override
    public ReadResponse read(SecurityAccessor accessor, ReadRequest request) throws ServiceException {
        return dataService.read(accessor, request);
    }

    @Override
    public UpdateResponse update(SecurityAccessor accessor, UpdateRequest request) throws ServiceException{
        return dataService.update(accessor, request);
    }

    @Override
    public UpdateFieldResponse update(SecurityAccessor accessor, UpdateFieldRequest request) {
        return dataService.update(accessor, request);
    }

    @Override
    public DeleteResponse delete(SecurityAccessor accessor, DeleteRequest request) throws ServiceException{
        return dataService.delete(accessor, request);
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryRequest request) throws ServiceException{
        return dataService.query(accessor, request);
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryIdsRequest request) throws ServiceException {
        return dataService.query(accessor, request);
    }

    @Override
    public InfoResponse info(InfoRequest request) {
        return dataService.info(request);
    }

    @Override
    public Access getAuthorizeAccess(SecurityAccessor accessor, Class<? extends Persistable> entityType, Access mask) {
        return dataService.getAuthorizeAccess(accessor, entityType, mask);
    }
}
