package com.taoswork.tallycheck.dataservice;

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

    public void setDataService(IDataService dataService){
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
    public NewInstanceResponse newInstance(NewInstanceRequest request) {
        return dataService.newInstance(request);
    }

    @Override
    public CreateResponse create(CreateRequest request) {
        return dataService.create(request);
    }

    @Override
    public ReadResponse read(ReadRequest request) throws ServiceException {
        return dataService.read(request);
    }

    @Override
    public UpdateResponse update(UpdateRequest request) {
        return dataService.update(request);
    }

    @Override
    public UpdateFieldResponse update(UpdateFieldRequest request) {
        return dataService.update(request);
    }

    @Override
    public DeleteResponse delete(DeleteRequest request) {
        return dataService.delete(request);
    }

    @Override
    public QueryResponse query(QueryRequest request) {
        return dataService.query(request);
    }

    @Override
    public InfoResponse info(InfoRequest request) {
        return dataService.info(request);
    }
}
