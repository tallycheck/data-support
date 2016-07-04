package com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource;

import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.provider.onmongo.common.client.ICoreAccessSensitiveRepo;
import com.taoswork.tallycheck.authority.provider.onmongo.filter.query.MorphiaQInterrupter;
import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public class XFileRepo<T extends Persistable> implements ICoreAccessSensitiveRepo {
    private final Class<T> entityClz;
    private final Datastore datastore;

    public XFileRepo(Datastore datastore, Class<T> entityClz) {
        this.datastore = datastore;
        this.entityClz = entityClz;
    }

    @Override
    public List query(KAccessibleScope accessibleScope, IKProtection resourceProtection) {
        if (accessibleScope == null) {
            return new ArrayList();
        }
        Query<T> q = datastore.createQuery(entityClz);
        Query<T> query = datastore.createQuery(entityClz);
        Criteria c = MorphiaQInterrupter.buildCriteria(query, accessibleScope, resourceProtection);
        if (c != null) {
            q.and(c);
        }
        return q.asList();
    }
}
