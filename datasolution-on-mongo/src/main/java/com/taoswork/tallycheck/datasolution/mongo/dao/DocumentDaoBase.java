package com.taoswork.tallycheck.datasolution.mongo.dao;

import com.taoswork.tallycheck.datasolution.core.entity.DaoBase;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.config.beanlist.IMongoBeanList;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class DocumentDaoBase extends DaoBase {
    @Resource(name = IMongoBeanList.DATASTORE_BEAN_NAME)
    protected Datastore datastore;

    @Resource(name = IMongoBeanList.DATASTORE_BEAN_NAME)
    protected AdvancedDatastore advancedDatastore;
}
