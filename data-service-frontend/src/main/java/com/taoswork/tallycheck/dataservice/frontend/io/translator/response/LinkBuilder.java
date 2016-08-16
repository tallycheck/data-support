package com.taoswork.tallycheck.dataservice.frontend.io.translator.response;

import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;
import com.taoswork.tallycheck.datadomain.base.restful.EntityActionPaths;
import com.taoswork.tallycheck.dataservice.frontend.io.request.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.*;
import com.taoswork.tallycheck.dataservice.frontend.io.response.range.QueryResultRange;
import io.mikael.urlbuilder.UrlBuilder;
import org.springframework.hateoas.Link;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class LinkBuilder {

    public static void buildLinkForInfoResults(EntityInfoRequest request, EntityResponse response) {
        response.add(new Link(request.getFullUri()));
    }

    public static void buildLinkForQueryResults(EntityQueryRequest request, EntityQueryResponse response) {
        appendEntityActionUris(request.getResourceName(), response);
        if (response.getErrors().containsError())
            return;

        QueryResultRange currentRange = response.getEntities().makeRange();
        QueryResultRange next = currentRange.next();
        QueryResultRange pre = currentRange.pre();
        response.add(new Link(request.getFullUri()));
        if (pre.isValid()) {
            UrlBuilder urlBuilder = UrlBuilder.fromString(request.getFullUri());
            if (pre.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, "" + pre.getStart());
            } else {
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_PREVIOUS));
        }
        if (next.isValid()) {
            UrlBuilder urlBuilder = UrlBuilder.fromString(request.getFullUri());
            if (next.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, "" + next.getStart());
            } else {
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_NEXT));
        }
    }

    public static void buildLinkForCreateFreshResults(EntityCreateFreshRequest request, EntityCreateFreshResponse response) {
        response.add(new Link(request.getFullUri()));
        appendEntityUriByAction(request, EntityAction.CREATE, response);
    }

    public static void buildLinkForCreateResults(EntityCreateRequest request, EntityCreateResponse response) {
        response.add(new Link(request.getFullUri()));
        appendEntityUriByAction(request, EntityAction.CREATE, response);
    }

    public static void buildLinkForReadResults(EntityReadRequest request, EntityReadResponse response) {
        response.add(new Link(request.getFullUri()));
        appendEntityActionUris(request.getResourceName(), response);
    }

    public static String buildLinkForReadInstance(String entityName) {
        return EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.READ);
    }

    private static void appendEntityUriByAction(EntityRequest request, EntityAction action, EntityResponse response) {
        appendEntityUriByAction(request.getResourceName(), action, response);
    }

    private static void appendEntityUriByAction(String entityName, EntityAction action, EntityResponse response) {
        String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
        response.add(new Link(uri).withRel(action.getType()));
    }

    /**
     * @param entityName, url with entitytype path, example: http://localhost:2222/{entityName}
     * @param response
     */
    private static void appendEntityActionUris(String entityName, EntityResponse response) {
        EntityAction [] actions = new EntityAction[]{
                EntityAction.INFO, EntityAction.QUERY, EntityAction.CREATE,
                EntityAction.READ, EntityAction.UPDATE, EntityAction.DELETE};
        for(EntityAction action : actions){
            appendEntityUriByAction(entityName, action, response);
        }
    }

}
