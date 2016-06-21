package com.taoswork.tallycheck.authority.solution.mockup.service;

import com.taoswork.tallycheck.authority.solution.mockup.service.datasource.AuthSolutionDatasourceConfiguration;
import com.taoswork.tallycheck.authority.solution.mockup.service.datasource.AuthSolutionPersistableConfiguration;
import com.taoswork.tallycheck.dataservice.annotations.DataService;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.core.MongoDataServiceBase;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@DataService
public class AuthSolutionDataService
        extends MongoDataServiceBase<
        AuthSolutionPersistableConfiguration,
        MongoDatasourceConfiguration> {

    public AuthSolutionDataService() {
        this(AuthSolutionDatasourceConfiguration.class);
    }

    public AuthSolutionDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new AuthSolutionDataServiceDefinition(),
                AuthSolutionPersistableConfiguration.class,
                dSrcConfClz);
    }
}
