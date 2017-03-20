package com.taoswork.tallycheck.datasolution.config;

import com.taoswork.tallycheck.datasolution.DatasourceDefinition;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public interface IDatasourceConfiguration {

    DatasourceDefinition datasourceDefinition();

    MetaService metadataService();

    MetaInfoService metaInfoService();

    EntityCopierService entityValueCopierService();

}
