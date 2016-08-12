package com.taoswork.tallycheck.dataservice.frontend.io.translator.response;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.EntityValidationException;
import com.taoswork.tallycheck.dataservice.exception.NoPermissionException;
import com.taoswork.tallycheck.dataservice.exception.NoSuchRecordException;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.frontend.dataio.FormEntity;
import com.taoswork.tallycheck.dataservice.frontend.io.request.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityErrors;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityInstanceResult;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityQueryResult;
import com.taoswork.tallycheck.dataservice.io.response.InstanceResponse;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import org.apache.commons.collections4.MultiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/19.
 */
public class ResponseTranslator {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseTranslator.class);

    private final MessageSource messageSource;

    public ResponseTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void translateInstanceRequest(EntityInstancePostRequest request,
                                         EntityResponse response) {
        FormEntity entity = request.getEntity();
        translateRequest(request, response);
        response.setEntityCeilingType(entity.getCeilingType());
    }

    public void translateRequest(EntityRequest request,
                                 EntityResponse response) {
        response//.setResourceName(request.getResource())
                .setEntityCeilingType(request.getEntityType())
                .setEntityType(request.getEntityType())
//            .setUri(request.getUri())
        //.setEntityUri(request.getEntityUri());
        ;
    }

    public void translateInfoResponse(EntityInfoRequest request,
                                      EntityInfoResponse response,
                                      Locale locale) {
        translateRequest(request, response);

    }

    public void translateQueryResponse(EntityQueryRequest request,
                                       CriteriaQueryResult<?> criteriaResult,
                                       ServiceException e,
                                       EntityQueryResponse response,
                                       Locale locale) {
        translateRequest(request, response);

        EntityQueryResult queryResult = ResultTranslator.convertQueryResult(request, criteriaResult);
        if (criteriaResult != null) {
            response.setEntityType(criteriaResult.getEntityType());
        }
        response.setEntities(queryResult);
        response.setFullQuery(request.getFullUri());

        handleServiceException(e, response, locale);
    }

    public void translateCreateFreshResponse(EntityCreateFreshRequest request,
                                             InstanceResponse result,
                                             ServiceException e,
                                             EntityCreateFreshResponse response,
                                             Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateCreateResponse(EntityCreateRequest request,
                                        InstanceResponse result,
                                        ServiceException e,
                                        EntityCreateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateReadResponse(EntityReadRequest request,
                                      InstanceResponse result,
                                      ServiceException e,
                                      EntityReadResponse response,
                                      Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateUpdateResponse(EntityUpdateRequest request,
                                        InstanceResponse result,
                                        ServiceException e,
                                        EntityUpdateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateDeleteResponse(EntityDeleteRequest request,
                                        boolean deleted,
                                        ServiceException e,
                                        EntityDeleteResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        response.setDeleted(deleted);
        handleServiceException(e, response, locale);
    }

    public void translateCollectionEntryCreateFreshResponse(CollectionEntryCreateFreshRequest request,
                                                            InstanceResponse result,
                                                            ServiceException e,
                                                            CollectionEntryCreateFreshResponse response,
                                                            Locale locale) {
        translateRequest(request, response);
        //       translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    private void translateInstanceResponse(EntityRequest request,
                                           InstanceResponse result,
                                           ServiceException e,
                                           EntityInstanceResponse response) {
        PersistableResult persistableResult = null;
        if (result != null) {
            persistableResult = new PersistableResult();
            persistableResult.setIdKey(result.getIdKey());
            persistableResult.setIdValue(result.getIdValue());
            persistableResult.setName(result.getName());
            persistableResult.setValue(result.getResult());
        } else if (result == null && e instanceof EntityValidationException) {
            persistableResult = ((EntityValidationException) e).getEntity();
        }
        if (persistableResult == null) {
            response.setEntityType(null);
            response.setEntity(null);
        } else {
            Persistable resultEntity = persistableResult.getValue();
            response.setEntityType(resultEntity.getClass());
            EntityInstanceResult instanceResult = ResultTranslator.convertPersistableResult(persistableResult);
            response.setEntity(instanceResult);
        }
    }

    private void handleServiceException(ServiceException e, EntityResponse response, Locale locale) {
        if (e != null) {
            EntityErrors errors = response.getErrors();
            if (e instanceof EntityValidationException) {
                EntityValidationException ve = (EntityValidationException) e;
                this.translateValidationError(ve, errors, locale);
            } else if (e instanceof NoSuchRecordException) {
                this.translateNoSuchRecordError((NoSuchRecordException) e, errors, locale);
            } else if (e instanceof NoPermissionException) {
                errors.setAuthorized(false);
                this.translateNoPermissionError((NoPermissionException) e, errors, locale);
            } else {
                this.translateUnhandledServiceError(e, errors, locale);
            }
        }
    }

    private String getMessage(String code, Object[] args, Locale locale) {
        if (args == null)
            args = new Object[]{};
        return this.messageSource.getMessage(code, args, code, locale);
    }

    private void translateNoSuchRecordError(NoSuchRecordException exception, EntityErrors errors, Locale locale) {
        String errorCode = exception.getMessageCode();
        Object[] args = exception.getArgs();
        String msg = this.getMessage(errorCode, args, locale);
        errors.addGlobalErrorMessage(msg);
    }

    private void translateNoPermissionError(NoPermissionException exception, EntityErrors errors, Locale locale) {
        String errorCode = exception.getMessageCode();
        Object[] args = exception.getArgs();
        String msg = this.getMessage(errorCode, args, locale);
        errors.addGlobalErrorMessage(msg);
    }

    private void translateValidationError(EntityValidationException validationErrors,
                                          EntityErrors errors, Locale locale) {
        List<String> verrors = validationErrors.getErrors();
        for(String error : verrors){
            errors.addGlobalErrorMessage(error);
        }

        for(String field : validationErrors.getErrorFields()){
            Collection<String> fieldMsgs = validationErrors.getFieldErrors(field);
            for(String msg : fieldMsgs){
                errors.addFieldErrorMessage(field, msg);
            }
        }

    }

    private void translateUnhandledServiceError(ServiceException serviceException, EntityErrors errors, Locale locale) {
        errors.addGlobalErrorMessage(serviceException.getMessage());
    }
}