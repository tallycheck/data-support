package com.taoswork.tallycheck.datasolution.mongo.config;

import com.mongodb.MongoClient;
import com.taoswork.tallycheck.datasolution.IDatasourceBeanConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.config.beanlist.IMongoBeanList;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.SecuredEntityAccess;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.impl.MongoEntityServiceImpl;
import com.taoswork.tallycheck.datasolution.mongo.core.metaaccess.MongoEntityMetaAccessBase;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
@Configuration
public class MongoDatasourceBeanConfiguration
        implements IDatasourceBeanConfiguration, IMongoBeanList {

    @Resource(name = MongoDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION)
    protected MongoDatasourceDefinition mongoDatasourceDefinition;

    @Resource(name = MongoPersistableConfiguration.PERSISTABLE_ENTITIES_BEAN_NAME)
    protected Class<?>[] persistableEntities;

    public MongoDatasourceBeanConfiguration() {
    }

    @Bean(name = MongoDatasourceDefinition.DATASTORE_BEAN_NAME)
    public AdvancedDatastore datastore() {
        final MongoDatasourceDefinition mdd = this.mongoDatasourceDefinition;

        final Morphia morphia = new Morphia();
        Class rootDataClass = mdd.getRootDataClass();
        if (rootDataClass != null) {
            morphia.mapPackageFromClass(mdd.getRootDataClass());
        }
        Class[] pEntities = persistableEntities;
        if (pEntities != null) {
            morphia.map(pEntities);
        }

        MongoClient client = new MongoClient(mdd.getServerAddress());
        final AdvancedDatastore datastore = (AdvancedDatastore) morphia.createDatastore(client, mdd.getDbName());
        datastore.ensureIndexes();
        return datastore;
    }

    @Bean(name = EntityMetaAccess.COMPONENT_NAME)
    public EntityMetaAccess entityMetaAccess() {
        MongoEntityMetaAccessBase metadataAccess = new MongoEntityMetaAccessBase();
        return metadataAccess;
    }

    @Bean(name = IEntityService.COMPONENT_NAME)
    public MongoEntityService mongoEntityService() {
        return new MongoEntityServiceImpl();
    }

    @Bean(name = SecuredEntityAccess.COMPONENT_NAME)
    public SecuredEntityAccess securedEntityAccess() {
        return new SecuredEntityAccess();
    }
}
