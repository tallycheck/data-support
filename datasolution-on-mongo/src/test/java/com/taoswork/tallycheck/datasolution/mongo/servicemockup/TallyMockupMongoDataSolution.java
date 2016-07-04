package com.taoswork.tallycheck.datasolution.mongo.servicemockup;

import com.taoswork.tallycheck.datasolution.annotations.DataSolutionMark;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.core.MongoDataSolutionBase;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.datasource.TallyMockupPersistableConfiguration;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@DataSolutionMark
public class TallyMockupMongoDataSolution
        extends MongoDataSolutionBase<
                        TallyMockupPersistableConfiguration,
                        MongoDatasourceConfiguration> {

    public TallyMockupMongoDataSolution() {
        this(TallyMockupMongoDatasourceConfiguration.class);
    }

    public TallyMockupMongoDataSolution(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyMockupMongoDataSolutionDefinition(),
                TallyMockupPersistableConfiguration.class,
                dSrcConfClz);
    }
}
