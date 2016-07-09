package com.taoswork.tallycheck.dataservice.frontend.service;

import com.taoswork.tallycheck.dataservice.frontend.io.request.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.*;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface IFrontEndEntityService {

    EntityInfoResponse getInfoResponse(EntityInfoRequest request, Locale locale);

    EntityQueryResponse query(EntityQueryRequest request, Locale locale);

    /**
     * Used in the 'get' method, returns an entity instance inside of EntityCreateFreshResponse
     *
     * @param request
     * @param locale
     * @return
     * @throws ServiceException
     */
    EntityCreateFreshResponse createFresh(EntityCreateFreshRequest request, Locale locale);

    EntityCreateResponse create(EntityCreateRequest request, Locale locale);

    EntityReadResponse read(EntityReadRequest readRequest, Locale locale);

    EntityUpdateResponse update(EntityUpdateRequest request, Locale locale);

    EntityDeleteResponse delete(EntityDeleteRequest deleteRequest, Locale locale);

    CollectionEntryCreateFreshResponse collectionEntryCreateFresh(CollectionEntryCreateFreshRequest request, Locale locale);
}
