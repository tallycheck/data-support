package com.taoswork.tallycheck.datasolution.mongo.core.entityservice;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface MongoEntityService extends IEntityService<PersistableDocument> {
    AdvancedDatastore getAdvancedDatastore();
    Datastore getDatastore();

}
