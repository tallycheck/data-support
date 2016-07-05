package com.taoswork.tallycheck.datasolution.mongo.config;

import com.taoswork.tallycheck.datasolution.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.core.entityprotect.valuecoper.MongoEntityCopierServiceImpl;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.mongo.service.MongoMetaInfoServiceImpl;
import com.taoswork.tallycheck.descriptor.mongo.service.MongoMetaServiceImpl;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;
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
