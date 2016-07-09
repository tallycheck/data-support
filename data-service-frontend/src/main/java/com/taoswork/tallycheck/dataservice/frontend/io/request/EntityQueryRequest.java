package com.taoswork.tallycheck.dataservice.frontend.io.request;


import com.taoswork.tallycheck.dataservice.frontend.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.query.PropertyFilterCriteria;
import com.taoswork.tallycheck.dataservice.query.PropertySortCriteria;

import java.net.URI;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryRequest extends EntityRequest implements Pageable {

    public static final int DEFAULT_REQUEST_MAX_RESULT_COUNT = 50;

    private CriteriaTransferObject criteriaTransferObject = new CriteriaTransferObject();

    public EntityQueryRequest(EntityTypeParameter entityTypeParam, URI fullUri) {
        super(entityTypeParam, fullUri);
    }

    @Override
    public long getStartIndex() {
        return criteriaTransferObject.getFirstResult();
    }

    public void setStartIndex(int startIndex) {
        criteriaTransferObject.setFirstResult(startIndex);
    }

    @Override
    public int getPageSize() {
        return criteriaTransferObject.getPageSize();
    }

    public void setPageSize(int pageSize) {
        criteriaTransferObject.setPageSize(pageSize);
    }

    public EntityQueryRequest appendSortCriteria(PropertySortCriteria sortCriteria) {
        criteriaTransferObject.addSortCriteria(sortCriteria);
        return this;
    }

    public EntityQueryRequest appendFilterCriteria(PropertyFilterCriteria filterCriteria) {
        criteriaTransferObject.addFilterCriteria(filterCriteria);
        return this;
    }

    public Collection<PropertySortCriteria> getSortCriterias() {
        return criteriaTransferObject.getSortCriterias();
    }

    public Collection<PropertyFilterCriteria> getFilterCriterias() {
        return criteriaTransferObject.getFilterCriteriasCollection();
    }
}
