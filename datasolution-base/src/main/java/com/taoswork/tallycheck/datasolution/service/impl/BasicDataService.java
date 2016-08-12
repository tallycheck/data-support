package com.taoswork.tallycheck.datasolution.service.impl;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.UnexpectedException;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.FieldValidationErrors;
import com.taoswork.tallycheck.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallycheck.dataservice.*;
import com.taoswork.tallycheck.dataservice.exception.EntityValidationErrorCodeException;
import com.taoswork.tallycheck.dataservice.exception.EntityValidationException;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import com.taoswork.tallycheck.dataservice.operator.Operator;
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
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
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

    @Resource(name = IDataSolution.ERROR_MESSAGE_SOURCE_BEAN_NAME)
    protected MessageSource errorMessageSource;

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
        setInstanceResponseByPersistableResult(result, response);
        if (result.getValue() != null) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    private void setInstanceResponseByPersistableResult(PersistableResult result, InstanceResponse response) {
        response.result = result.getValue();
        response.setIdKey(result.getIdKey());
        response.setIdValue(result.getIdValue());
        response.setName(result.getName());
    }

    private static Entity getEntity(InstanceRequest request) {
        RequestEntity requestEntity = request.entity;
        return new Entity(requestEntity.getType(), requestEntity.getProps());
    }

    @Override
    public CreateResponse create(Operator operator, SecurityAccessor accessor, CreateRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        Entity entity = getEntity(request);
        try {
            Persistable persistable = entityTranslator.translate(entityMetaAccess, entity, "");
            PersistableResult result = entityService.create(operator, accessor, persistable);
            CreateResponse response = new CreateResponse();
            setInstanceResponseByPersistableResult(result, response);
            if (result.getValue() != null) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
            return response;
        } catch (TranslateException e) {
            throw new ServiceException(e);
        } catch (ServiceException e) {
            throw makeLocalizedServiceException(e, request);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ReadResponse read(Operator operator, SecurityAccessor accessor, ReadRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        ReadResponse response = new ReadResponse();
        PersistableResult result = entityService.read(operator, accessor, entityCls, request.getId(), response.references);
        setInstanceResponseByPersistableResult(result, response);
        if (result.getValue() != null) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public UpdateResponse update(Operator operator, SecurityAccessor accessor, UpdateRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        Entity entity = getEntity(request);
        try {
            Persistable persistable = entityTranslator.translate(entityMetaAccess, entity, "");
            LOGGER.debug("check if id is set");
            PersistableResult result = entityService.update(operator, accessor, persistable);
            UpdateResponse response = new UpdateResponse();
            setInstanceResponseByPersistableResult(result, response);
            if (result.getValue() != null) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
            return response;
        } catch (TranslateException e) {
            throw new ServiceException(e);
        } catch (ServiceException e) {
            throw makeLocalizedServiceException(e, request);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UpdateFieldResponse update(Operator operator, SecurityAccessor accessor, UpdateFieldRequest request) {
        return null;
    }

    @Override
    public DeleteResponse delete(Operator operator, SecurityAccessor accessor, DeleteRequest request) throws ServiceException {
        Class entityCls = adjustEntityTypeToClass(request.getType());
        boolean result = entityService.delete(operator, accessor, entityCls, request.getId());
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
        response.setSuccess(true);
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
        IEntityInfo entityInfo = entityService.describe(entityCls, request.withHierarchy, convertInfoType(request.infoType), request.getLocale());
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
    public Access getAuthorizeAccess(SecurityAccessor accessor, String entityType, Access mask) {
        String guardianName = entityMetaAccess.getPermissionGuardian(entityType);
        return securityVerifier.getAllPossibleAccess(accessor, guardianName, mask);
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

    private ServiceException makeLocalizedServiceException(ServiceException e, Request req) {
        if (e instanceof EntityValidationErrorCodeException) {
            EntityValidationErrorCodeException vece = (EntityValidationErrorCodeException) e;
            Locale locale = req.getLocale();
            PersistableResult entity = vece.getEntity();
            EntityValidationErrors validationError = vece.getEntityValidationError();

            EntityValidationException eve = new EntityValidationException(entity);
            if (!validationError.isValid()) {
                this.translateValidationError(validationError, eve, locale);
            } else {
                this.translateUnhandledServiceError(e, eve, locale);
            }

            return eve;
        } else {
            return e;
        }
    }

    private void translateUnhandledServiceError(ServiceException e, EntityValidationException eve, Locale locale) {
        eve.addError(e.getMessage());
    }

    private void translateValidationError(EntityValidationErrors validationErrors,
                                          EntityValidationException eve, Locale locale) {
        MessageSource ms = errorMessageSource;
        if (!validationErrors.isValid()) {
            Collection<FieldValidationErrors> fieldErrorsCollection = validationErrors.getFieldErrors();
            for (FieldValidationErrors fieldErrors : fieldErrorsCollection) {
                String fieldName = fieldErrors.getFieldName();
                Collection<ValidationError> errorMsgs = fieldErrors.getErrors();
                for (ValidationError emsg : errorMsgs) {
                    String msg = ms.getMessage(emsg.getCode(), emsg.getArgs(), locale);
                    eve.addFieldError(fieldName, msg);
                }
            }
            for (ValidationError emsg : validationErrors.getErrors()) {
                String msg = ms.getMessage(emsg.getCode(), emsg.getArgs(), locale);
                eve.addError(msg);
            }
        }

    }
}
