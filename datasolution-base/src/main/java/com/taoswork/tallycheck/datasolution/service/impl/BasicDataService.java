package com.taoswork.tallycheck.datasolution.service.impl;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.UnexpectedException;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.*;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.security.ISecurityVerifier;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.IEntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.TranslateException;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.info.IEntityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class BasicDataService implements IDataService {
    public final static String COMPONENT_NAME = "BasicDataService";
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataService.class);

    @Resource(name = IDataSolution.DATA_SOLUTION_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = IEntityService.COMPONENT_NAME)
    protected IEntityService entityService;

    @Resource(name = IEntityTranslator.COMPONENT_NAME)
    protected IEntityTranslator entityTranslator;

    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    @Resource(name = ISecurityVerifier.COMPONENT_NAME)
    protected ISecurityVerifier securityVerifier;

    private IDataSolution dataSolution;

    private Map<String, Class<? extends Persistable>> entityResourceNameToClz = new HashMap<String, Class<? extends Persistable>>();

    public void setDataSolution(IDataSolution dataSolution) {
        this.dataSolution = dataSolution;

        try {
            for (EntityType entityType : getEntityTypes()) {
                String resourceTypeName = entityType.getResourceName();
                String resourceInterfaceName = entityType.getEntityInterfaceName();
                Class<? extends Persistable> resourceInterface = (Class<? extends Persistable>) Class.forName(resourceInterfaceName);
                entityResourceNameToClz.put(resourceInterfaceName, resourceInterface);
                entityResourceNameToClz.put(resourceTypeName, resourceInterface);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Entity Type interface definition not valid.");
        }
    }

    private Class<? extends Persistable> adjustEntityTypeToClass(String entityType) {
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
    public NewInstanceResponse newInstance(NewInstanceRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        PersistableResult result = entityService.makeDissociatedPersistable(entityCls);
        NewInstanceResponse response = new NewInstanceResponse();
        if (result.getValue() != null) {
            response.setSuccess(true);
            response.result = result.getValue();
        } else {
            response.setSuccess(false);
            response.result = null;
        }
        return response;
    }

    private static Entity getEntity(InstanceRequest request){
        RequestEntity requestEntity = request.entity;
        return new Entity(requestEntity.getType(), requestEntity.getProps());
    }
    @Override
    public CreateResponse create(SecurityAccessor accessor, CreateRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        Entity entity = getEntity(request);
        try {
            Persistable persistable = entityTranslator.translate(entityMetaAccess, entity, "");
            PersistableResult result = entityService.create(accessor, persistable);
            CreateResponse response = new CreateResponse();
            if (result.getValue() != null) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
            return response;
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public ReadResponse read(SecurityAccessor accessor, ReadRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        ReadResponse response = new ReadResponse();
        PersistableResult result = entityService.read(accessor, entityCls, request.getId(), response.references);
        if (result.getValue() != null) {
            response.setSuccess(true);
            response.result = result.getValue();
        } else {
            response.setSuccess(false);
            response.result = null;
        }
        return response;
    }

    @Override
    public UpdateResponse update(SecurityAccessor accessor, UpdateRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        Entity entity = getEntity(request);
        try {
            Persistable persistable = entityTranslator.translate(entityMetaAccess, entity, "");
            LOGGER.debug("check if id is set");
            PersistableResult result = entityService.update(accessor, persistable);
            UpdateResponse response = new UpdateResponse();
            if (result.getValue() != null) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
            return response;
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UpdateFieldResponse update(SecurityAccessor accessor, UpdateFieldRequest request) {
        return null;
    }

    @Override
    public DeleteResponse delete(SecurityAccessor accessor, DeleteRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        boolean result = entityService.delete(accessor, entityCls, request.getId());
        DeleteResponse response = new DeleteResponse();
        if (result) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        QueryResponse response = new QueryResponse();
        CriteriaQueryResult result = entityService.query(accessor, entityCls, request.query, response.references, CopyLevel.List);
        response.result = result;
        return response;
    }

    @Override
    public QueryResponse query(SecurityAccessor accessor, QueryIdsRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        QueryResponse response = new QueryResponse();
        CriteriaQueryResult result = entityService.queryIds(accessor, entityCls, request.ids, response.references, CopyLevel.List);
        response.result = result;
        return response;
    }

    @Override
    public InfoResponse info(InfoRequest request) {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        IEntityInfo entityInfo = entityService.describe(entityCls, request.withHierarchy, convertInfoType(request.infoType), request.locale);
        InfoResponse response = new InfoResponse();
        if (entityInfo != null) {
            response.setSuccess(true);
            response.info = entityInfo;
        } else {
            response.setSuccess(false);
            response.info = null;
        }
        return response;
    }

    @Override
    public Access getAuthorizeAccess(SecurityAccessor accessor, Class<? extends Persistable> entityType, Access mask) {
        Class guardian = entityMetaAccess.getPermissionGuardian(entityType);
        String guardianName = guardian.getName();
        return securityVerifier.getAllPossibleAccess(accessor, guardianName,mask);
    }

    private static EntityInfoType convertInfoType(InfoType infoType) {
        switch (infoType) {
            case Main:
                return EntityInfoType.Main;
            case Full:
                return EntityInfoType.Full;
            case Grid:
                return EntityInfoType.Grid;
            case Form:
                return EntityInfoType.Form;
            default:
                throw new UnexpectedException();
        }
    }
}
