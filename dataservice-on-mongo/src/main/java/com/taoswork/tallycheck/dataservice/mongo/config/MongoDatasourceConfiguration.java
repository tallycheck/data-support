package com.taoswork.tallycheck.dataservice.mongo.config;

import com.taoswork.tallycheck.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.dataservice.mongo.core.entityprotect.valuecoper.MongoEntityCopierServiceImpl;
import com.taoswork.tallycheck.dataservice.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;
import com.taosworks.tallycheck.descriptor.mongo.service.MongoMetaInfoServiceImpl;
import com.taosworks.tallycheck.descriptor.mongo.service.MongoMetaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public abstract class MongoDatasourceConfiguration implements IDatasourceConfiguration {

    protected abstract MongoDatasourceDefinition createDatasourceDefinition();

    @Bean(name = DATA_SOURCE_PATH_DEFINITION)
    public MongoDatasourceDefinition mongoDatasourceDefinition() {
        return createDatasourceDefinition();
    }

    @Override
    @Bean(name = MetaService.SERVICE_NAME)
    public MetaService metadataService() {
        return new MongoMetaServiceImpl();
    }

    @Override
    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new MongoMetaInfoServiceImpl();
        return metaInfoService;
    }

    @Override
    @Bean(name = EntityCopierService.COMPONENT_NAME)
    public EntityCopierService entityValueCopierService() {
        return new MongoEntityCopierServiceImpl();
    }
}
