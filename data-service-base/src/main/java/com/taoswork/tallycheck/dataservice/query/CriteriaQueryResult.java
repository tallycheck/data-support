package com.taoswork.tallycheck.dataservice.query;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.general.extension.collections.CollectionUtility;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class CriteriaQueryResult<T extends Persistable> implements Serializable {
    //the entity type, in interface / class
    private final String entityType;
    //
    private List<T> entityCollection;
    private Long totalCount;
    private long startIndex;

    public CriteriaQueryResult(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityType() {
        return entityType;
    }

    public List<T> getEntityCollection() {
        return entityCollection;
    }

    public CriteriaQueryResult setEntityCollection(List<T> entityCollection) {
        this.entityCollection = entityCollection;
        return this;
    }

    public int fetchedCount() {
        if (CollectionUtility.isEmpty(entityCollection)) {
            return 0;
        }
        return entityCollection.size();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public CriteriaQueryResult setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public CriteriaQueryResult setStartIndex(long startIndex) {
        this.startIndex = startIndex;
        return this;
    }
}
