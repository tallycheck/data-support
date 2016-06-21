package com.taoswork.tallycheck.dataservice.mongo.servicemockup;

import com.taoswork.tallycheck.dataservice.annotations.DataService;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.core.MongoDataServiceBase;
import com.taoswork.tallycheck.dataservice.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.servicemockup.datasource.TallyMockupPersistableConfiguration;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@DataService
public class TallyMockupMongoDataService
        extends MongoDataServiceBase<
                TallyMockupPersistableConfiguration,
                MongoDatasourceConfiguration> {

    public TallyMockupMongoDataService() {
        this(TallyMockupMongoDatasourceConfiguration.class);
    }

    public TallyMockupMongoDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyMockupMongoDataServiceDefinition(),
                TallyMockupPersistableConfiguration.class,
                dSrcConfClz);
    }
}
