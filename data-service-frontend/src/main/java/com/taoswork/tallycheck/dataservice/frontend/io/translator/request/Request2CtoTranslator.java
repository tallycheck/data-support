package com.taoswork.tallycheck.dataservice.frontend.io.translator.request;

import com.taoswork.tallycheck.dataservice.frontend.io.request.EntityQueryRequest;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class Request2CtoTranslator {
    public static CriteriaTransferObject translate(EntityQueryRequest request) {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.setFirstResult(request.getStartIndex());
        cto.setPageSize(request.getPageSize());
        cto.addFilterCriterias(request.getFilterCriterias());
        cto.addSortCriterias(request.getSortCriterias());

        return cto;
    }
}
