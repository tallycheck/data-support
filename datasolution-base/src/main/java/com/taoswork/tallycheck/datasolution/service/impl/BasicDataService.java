package com.taoswork.tallycheck.datasolution.service.impl;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.EntityType;
import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.service.IEntityService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class BasicDataService implements IDataService {
    public final static String COMPONENT_NAME = "BasicDataService";

    @Resource(name = IDataSolution.DATA_SOLUTION_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = IEntityService.COMPONENT_NAME)
    protected IEntityService entityService;

    private IDataSolution dataSolution;

    private Map<String, Class<? extends Persistable>> entityResourceNameToClz = new HashMap<String, Class<? extends Persistable>>();

    public void setDataSolution(IDataSolution dataSolution) {
        this.dataSolution = dataSolution;

        try {
            for(EntityType entityType : getEntityTypes()){
                String resourceTypeName = entityType.getResourceName();
                String resourceInterfaceName = entityType.getEntityInterfaceName();
                Class<? extends Persistable> resourceInterface = (Class<? extends Persistable>)Class.forName(resourceInterfaceName);
                entityResourceNameToClz.put(resourceInterfaceName, resourceInterface);
                entityResourceNameToClz.put(resourceTypeName, resourceInterface);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Entity Type interface definition not valid.");
        }
    }

    private Class<? extends Persistable> adjustEntityTypeToClass(String entityType){
        return entityResourceNameToClz.get(entityType);
    }

    @Override
    public String getName() {
        return dataServiceName;
    }

    @Override
    public Collection<EntityType> getEntityTypes() {
        return dataSolution.getEntityTypes();
    }

    @Override
    public NewInstanceResponse newInstance(NewInstanceRequest request) {
        return null;
    }

    @Override
    public CreateResponse create(CreateRequest request) {
        return null;
    }

    @Override
    public ReadResponse read(ReadRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        PersistableResult result = entityService.read(entityCls, request.getId());
        ReadResponse response = new ReadResponse();
        if(result.getValue() != null){
            response.setSuccess(true);
            response.result = result.getValue();
        }else {
            response.setSuccess(false);
            response.result = null;
        }
        return response;
    }

    @Override
    public UpdateResponse update(UpdateRequest request) {
        return null;
    }

    @Override
    public UpdateFieldResponse update(UpdateFieldRequest request) {
        return null;
    }

    @Override
    public DeleteResponse delete(DeleteRequest request) {
        return null;
    }

    @Override
    public QueryResponse query(QueryRequest request) {
        return null;
    }

    @Override
    public InfoResponse info(InfoRequest request) {
        return null;
    }
}
