package com.taoswork.tallycheck.datasolution.jpa;

import com.taoswork.tallycheck.datasolution.jpa.core.dao.EntityDao;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public final class DynamicDataServiceRoot {
    /**
     *     JpaEntityService
     *     ({@link JpaEntityService})
     *          a. CRUD support for entities (Service level)
     *          b. depends on EntityMetaAccess & EntityDao
     *
     *
     *
     *
     *     EntityMetaAccess
     *     ({@link EntityMetaAccess})
     *          a. List classes of a super class (or interface)
     *          b. depends on EntityManager
     *
     *     EntityDao
     *     ({@link EntityDao})
     *          a. CRUD support for entities (DAO level)
     *          b. depends on EntityManager
     *
     *
     */

}
