package com.taoswork.tallycheck.authority.provider.onmongo.client.service;

import com.taoswork.tallycheck.authority.provider.onmongo.client.service.datasource.AuthSolutionPersistableConfiguration;
import com.taoswork.tallycheck.authority.provider.onmongo.client.service.datasource.AuthSolutionDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.annotations.DataSolutionMark;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.core.MongoDataSolutionBase;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@DataSolutionMark
public class AuthSolutionDataSolution
        extends MongoDataSolutionBase<
        AuthSolutionPersistableConfiguration,
                MongoDatasourceConfiguration> {

    public AuthSolutionDataSolution() {
        this(AuthSolutionDatasourceConfiguration.class);
    }

    public AuthSolutionDataSolution(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new AuthSolutionDataSolutionDefinition(),
                AuthSolutionPersistableConfiguration.class,
                dSrcConfClz);
    }
}
