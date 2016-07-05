package com.taoswork.tallycheck.datasolution.jpa.config;

import com.taoswork.tallycheck.datasolution.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.jpa.JpaDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.jpa.core.entityprotect.valuecoper.JpaEntityCopierServiceImpl;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.jpa.service.JpaMetaInfoServiceImpl;
import com.taoswork.tallycheck.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;
import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class JpaDatasourceConfiguration implements IDatasourceConfiguration {

    protected abstract JpaDatasourceDefinition createDatasourceDefinition();

    @Bean(name = DATA_SOURCE_PATH_DEFINITION)
    public JpaDatasourceDefinition mongoDatasourceDefinition() {
        return createDatasourceDefinition();
    }

    @Override
    @Bean(name = MetaService.SERVICE_NAME)
    public MetaService metadataService() {
        return new JpaMetaServiceImpl();
    }

    @Override
    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new JpaMetaInfoServiceImpl();
        return metaInfoService;
    }

    @Override
    @Bean(name = EntityCopierService.COMPONENT_NAME)
    public EntityCopierService entityValueCopierService() {
        return new JpaEntityCopierServiceImpl();
    }
}
