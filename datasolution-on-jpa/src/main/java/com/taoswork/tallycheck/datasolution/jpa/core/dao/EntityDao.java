package com.taoswork.tallycheck.datasolution.jpa.core.dao;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public interface EntityDao {
    public static final String COMPONENT_NAME = "EntityDao";

//    void flush();
//
//    void detach(Object entity);
//
//    void refresh(Object entity);
//
//    void clear();

    <T extends Persistable> T create(T entity);

    <T extends Persistable> T read(Class<T> entityType, Object key);

    <T extends Persistable> T update(T entity);

    <T extends Persistable> void delete(T entity);

    <T extends Persistable> CriteriaQueryResult<T> query(Class<T> entityType, CriteriaTransferObject query);

}
