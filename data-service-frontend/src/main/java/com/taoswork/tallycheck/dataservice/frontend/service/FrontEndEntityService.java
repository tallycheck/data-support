package com.taoswork.tallycheck.dataservice.frontend.service;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;
import com.taoswork.tallycheck.datadomain.base.restful.EntityActionPaths;
import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.frontend.IProtectedAccessContext;
import com.taoswork.tallycheck.dataservice.frontend.io.request.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityInfoResult;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.InfoTypeOption;
import com.taoswork.tallycheck.dataservice.frontend.io.translator.request.Request2CtoTranslator;
import com.taoswork.tallycheck.dataservice.frontend.io.translator.response.ActionsBuilder;
import com.taoswork.tallycheck.dataservice.frontend.io.translator.response.LinkBuilder;
import com.taoswork.tallycheck.dataservice.frontend.io.translator.response.ResponseTranslator;
import com.taoswork.tallycheck.dataservice.frontend.io.translator.response.ResultTranslator;
import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;
import com.taoswork.tallycheck.dataservice.manage.DataServiceManager;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.general.solution.exception.UnImplementedException;
import com.taoswork.tallycheck.general.solution.reference.EntityFetchException;
import com.taoswork.tallycheck.general.solution.reference.EntityRecords;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;
import com.taoswork.tallycheck.general.solution.reference.IEntityRecordsFetcher;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.general.extension.collections.MapBuilder;
import com.taoswork.tallycheck.info.IEntityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.hateoas.UriTemplate;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class FrontEndEntityService implements IFrontEndEntityService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FrontEndEntityService.class);

    private final IDataService dataService;
    private final DataServiceManager dataServiceManager;
    private final IProtectedAccessContext protectedAccessContext;

    public FrontEndEntityService(
            DataServiceManager dataServiceManager,
            IDataService dataService,
            IProtectedAccessContext protectedAccessContext) {
        this.dataServiceManager = dataServiceManager;
        this.dataService = dataService;
        this.protectedAccessContext = protectedAccessContext;
//        this.errorMessageSource = dataService.getService(IDataService.ERROR_MESSAGE_SOURCE_BEAN_NAME);
//        this.translator = dataServiceManagezxdfgtrr.getEntityTranslator();
    }

    public static FrontEndEntityService newInstance(DataServiceManager dataServiceManager, IDataService dataService,
                                                    IProtectedAccessContext protectedAccessContext) {
        return new FrontEndEntityService(dataServiceManager, dataService,
                protectedAccessContext);
    }

    private void appendAuthorizedActions(EntityRequest request, EntityResponse response, ActionsBuilder.CurrentStatus currentStatus) {
        Access access = dataService.getAuthorizeAccess(accessor(), request.getEntityType().getName(), Access.Crudq);
        Collection<EntityAction> actions = ActionsBuilder.makeActions(access, currentStatus);
        Collection<String> actionsString = EntityAction.convertToTypes(actions, new HashSet<String>());
        response.setActions(actionsString);
    }

    private void appendInfoFields(EntityRequest request, EntityResponse response, Locale locale, InfoTypeOption infoTypeOption, boolean withHierarchy) {
        final Class<? extends Persistable> useType;

        switch (infoTypeOption) {
            case UseCeiling:
                useType = response.getEntityCeilingType();
                break;
            case UseFactual:
                useType = response.getEntityType();
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (useType == null)
            return;

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()) {
            InfoRequest infoRequest = new InfoRequest(useType, locale);
            infoRequest.withHierarchy = withHierarchy;
            infoRequest.infoType = InfoTypeConvertor.convert(infoType);
            InfoResponse infoResponse = dataService.info(infoRequest);
            entityInfos.add(infoResponse.info);
        }

        EntityInfoResult infoResult = null;
        for (IEntityInfo entityInfo : entityInfos) {
            if (infoResult == null) {
                infoResult = ResultTranslator.convertInfoResult(request, response);
            }
            if (entityInfo != null) {
                infoResult.getBasic().setIdFieldIfEmpty(entityInfo.getIdField())
                        .setNameFieldIfEmpty(entityInfo.getNameField());
                infoResult.addDetail(entityInfo.getInfoType(), entityInfo);
            }
        }

        response.setInfos(infoResult);
    }

    private ResponseTranslator responseTranslator() {
        MessageSource mm = new MessageSource() {
            @Override
            public String getMessage(String s, Object[] objects, String s1, Locale locale) {
                return "mm";
            }

            @Override
            public String getMessage(String s, Object[] objects, Locale locale) throws NoSuchMessageException {
                return "mm";
            }

            @Override
            public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
                return "mm";
            }
        };
        //errorMessageSource
        return new ResponseTranslator(mm);
    }

    private SecurityAccessor accessor() {
        String currentPersonId = protectedAccessContext.getCurrentPersonId();
        ProtectionScope currentPS = protectedAccessContext.getCurrentProtectionScope();
        return new SecurityAccessor(currentPS, currentPersonId);
    }

    private Operator operator(){
        return makeOperator(protectedAccessContext);
    }


    public static Operator makeOperator(IProtectedAccessContext accessContext){
        Operator op = new Operator();
        op.administrator = accessContext.isAdministrator();
        op.buId = accessContext.getCurrentBu();
        op.personId = accessContext.getCurrentPersonId();
        op.employeeId = accessContext.getCurrentEmployeeId();
        return op;
    }

    @Override
    public EntityInfoResponse getInfoResponse(EntityInfoRequest request, Locale locale) {
        EntityInfoResponse response = new EntityInfoResponse();
        responseTranslator().translateInfoResponse(request, response, locale);
        this.appendInfoFields(request, response, locale, InfoTypeOption.UseCeiling, false);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
        LinkBuilder.buildLinkForInfoResults(request, response);
        return response;
    }

    @Override
    public EntityQueryResponse query(EntityQueryRequest request, Locale locale) {
        Class<? extends Persistable> entityType = request.getEntityType();
        EntityQueryResponse response = new EntityQueryResponse(request.getUri());
        CriteriaQueryResult<? extends Persistable> result = null;
        ServiceException se = null;
        try {
            CriteriaTransferObject cto = Request2CtoTranslator.translate(request);
            QueryRequest queryRequest = new QueryRequest(request.makeRequest());
            queryRequest.query = cto;
            QueryResponse queryResponse = dataService.query(accessor(), queryRequest);
            result = queryResponse.result;

            ExternalReference externalReference = queryResponse.references;
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference, locale);
            }
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateQueryResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseCeiling, true);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
            LinkBuilder.buildLinkForQueryResults(request, response);
        }
        return response;
    }

    @Override
    public EntityCreateFreshResponse createFresh(EntityCreateFreshRequest request, Locale locale) {
        EntityCreateFreshResponse response = new EntityCreateFreshResponse(request.getUri());
        NewInstanceResponse newInstanceResponse = null;
        ServiceException se = null;
        try {
            Class<? extends Persistable> entityType = request.getEntityType();
            NewInstanceRequest newInstanceRequest = new NewInstanceRequest(request.makeRequest());
            newInstanceResponse = dataService.newInstance(newInstanceRequest);
//            response.setEntity(newInstanceResponse.getResult());
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateCreateFreshResponse(request, newInstanceResponse, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
            LinkBuilder.buildLinkForNewInstanceResults(request, response);
        }
        return response;
    }

    @Override
    public EntityCreateResponse create(EntityCreateRequest request, Locale locale) {
        String beanUriTemplate = LinkBuilder.buildLinkForReadInstance(request.getResourceName());
        EntityCreateResponse response = new EntityCreateResponse(request.getUri(), beanUriTemplate);
        CreateResponse createResponse = null;
        ServiceException se = null;
        try {
            RequestEntity requestEntity = request.getEntity().requestEntity();
            CreateRequest createRequest = new CreateRequest(requestEntity, locale);
            createResponse = dataService.create(operator(), accessor(), createRequest);

            Map<String, Object> m = new MapBuilder<String, Object>().append(EntityActionPaths.ID_KEY, createResponse.getIdValue());
            String beanUri = (new UriTemplate(beanUriTemplate)).expand(m).toString();
            response = new EntityCreateResponse(request.getUri(), beanUri);
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateCreateResponse(request, createResponse, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        }
        return response;
    }

    @Override
    public EntityReadResponse read(EntityReadRequest request, Locale locale) {
        Class<? extends Persistable> entityType = request.getEntityType();
        EntityReadResponse response = new EntityReadResponse(request.getUri());
        ReadResponse readResponse = null;
        ServiceException se = null;
        try {
            ReadRequest readRequest = new ReadRequest(request.makeRequest());
            readRequest.setId(request.getId());
            readResponse = dataService.read(operator(), accessor(), readRequest);
            ExternalReference externalReference = readResponse.references;
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference, locale);
            }
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateReadResponse(request, readResponse, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
            LinkBuilder.buildLinkForReadResults(request, response);
        }
        return response;
    }

    @Override
    public EntityUpdateResponse update(EntityUpdateRequest request, Locale locale) {
        EntityUpdateResponse response = new EntityUpdateResponse(request.getUri());
        PersistableResult result = null;
        UpdateResponse updateResponse = null;
        ServiceException se = null;
        try {
            RequestEntity requestEntity = request.getEntity().requestEntity();
            UpdateRequest updateRequest = new UpdateRequest(requestEntity, locale);
            updateResponse = dataService.update(operator(), accessor(), updateRequest);
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateUpdateResponse(request, updateResponse, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
        }
        return response;
    }

    @Override
    public EntityDeleteResponse delete(EntityDeleteRequest request, Locale locale) {
        Class<? extends Persistable> entityType = request.getEntityType();
        EntityDeleteResponse response = new EntityDeleteResponse(request.getUri());
        boolean deleted = false;
        ServiceException se = null;
        try {
            DeleteRequest deleteRequest = new DeleteRequest(request.makeRequest());
            deleteRequest.setId(request.getId());
            DeleteResponse deleteResponse = dataService.delete(operator(), accessor(), deleteRequest);
            deleted = deleteResponse.isSuccess();
        } catch (ServiceException e) {
            se = e;
        } catch (Exception e) {
            se = new ServiceException(e);
        } finally {
            responseTranslator().translateDeleteResponse(request, deleted, se, response, locale);
        }
        return response;
    }

    @Override
    public CollectionEntryCreateFreshResponse collectionEntryCreateFresh(CollectionEntryCreateFreshRequest request, Locale locale) {
        CollectionEntryCreateFreshResponse response = new CollectionEntryCreateFreshResponse(request.getUri());
        NewInstanceResponse result = null;
        ServiceException se = null;
        try {
            Class<?> entityType = request.getEntryPresentationClass();
            throw new UnImplementedException();
            //           result = entityService.makeDissociatedPersistable(request.getEntryPresentationClass());
//        } catch (ServiceException e) {
//            se = e;
        } finally {
            responseTranslator().translateCollectionEntryCreateFreshResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        }
//        return response;
    }

    private void fillExternalReference(ExternalReference externalReference, final Locale locale) throws ServiceException {
        if (null != externalReference) {
            try {
                Map<String, EntityRecords> records = externalReference.calcReferenceValue(new IEntityRecordsFetcher() {
                    @Override
                    public Map<Object, Object> fetch(Class entityType, Collection<Object> ids) throws EntityFetchException {
                        try {
                            String entityTypeName = entityType.getName();
                            Map<Object, Object> result = new HashMap<Object, Object>();
                            IDataService externalDataService = dataServiceManager.getDataService(entityTypeName);

                            CriteriaTransferObject cto = new CriteriaTransferObject();
                            cto.setPageSize(ids.size());
                            List<String> idStrings = new ArrayList<String>();
                            for (Object id : ids) {
                                if (id != null)
                                    idStrings.add(id.toString());
                            }
                            QueryIdsRequest queryIdsRequest = new QueryIdsRequest(entityTypeName, locale);
                            queryIdsRequest.ids = idStrings;
                            QueryResponse queryResponse = externalDataService.query(accessor(), queryIdsRequest);

                            CriteriaQueryResult cqr = queryResponse.result;
                            if (cqr.getTotalCount() > 0) {
                                List externalEntities = cqr.getEntityCollection();
                                for (Object extEntity : externalEntities) {
                                    Object id = ((Persistable) extEntity).getInstanceId();
                                    result.put(id, extEntity);
                                }
                            }

                            return result;
                        } catch (ServiceException e) {
                            throw new EntityFetchException(e);
                        }
                    }
                });
                externalReference.fillReferencingSlots(records);
            } catch (EntityFetchException e) {
                Throwable innerException = e.getCause();
                if (innerException instanceof ServiceException) {
                    throw (ServiceException) innerException;
                } else {
                    throw new ServiceException(innerException);
                }
            }
        }
    }

}