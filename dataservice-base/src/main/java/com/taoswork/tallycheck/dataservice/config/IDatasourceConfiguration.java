package com.taoswork.tallycheck.dataservice.config;

import com.taoswork.tallycheck.dataservice.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public interface IDatasourceConfiguration {
    public static final String DATA_SOURCE_PATH_DEFINITION = "datasourceDefinition";

    MetaService metadataService();

    MetaInfoService metaInfoService();

    EntityCopierService entityValueCopierService();

}
