package com.taoswork.tallycheck.dataservice.frontend.io.translator.response;

import com.taoswork.tallycheck.dataservice.frontend.io.request.EntityQueryRequest;
import com.taoswork.tallycheck.dataservice.frontend.io.request.EntityRequest;
import com.taoswork.tallycheck.dataservice.frontend.io.response.EntityResponse;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityInfoResult;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityInstanceResult;
import com.taoswork.tallycheck.dataservice.frontend.io.response.result.EntityQueryResult;
import com.taoswork.tallycheck.dataservice.io.response.InstanceResponse;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class ResultTranslator {
    public static EntityInfoResult convertInfoResult(EntityRequest request,
                                                     EntityResponse response) {
        EntityInfoResult result = new EntityInfoResult();
        result.getBasic().setCeilingType(request.getEntityType())
                .setType(response.getEntityType())
                .setBeanUri(LinkBuilder.buildLinkForReadInstance(request.getResourceName()));
        return result;
    }

    public static EntityQueryResult convertQueryResult(EntityQueryRequest request,
                                                       CriteriaQueryResult<?> criteriaResult) {
        EntityQueryResult result = new EntityQueryResult();
        result.setStartIndex(request.getStartIndex())
                .setPageSize(request.getPageSize());
        if (criteriaResult != null) {
            result.setTotalCount(criteriaResult.getTotalCount())
                    .setBeans(criteriaResult.getEntityCollection());
        }
        return result;
    }

    public static EntityInstanceResult convertInstanceResult(InstanceResponse er) {
        EntityInstanceResult result = new EntityInstanceResult();
        result.setBean(er.result)
                .setName(er.getName())
                .setIdKey(er.getIdKey())
                .setIdValue(er.getIdValue());
        return result;
    }
}
