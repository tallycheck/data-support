package com.taoswork.tallycheck.dataservice.mongo.servicemockup.datasource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinitionBase;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinitionForTest;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class TallyMockupMongoDatasourceConfiguration extends MongoDatasourceConfiguration {
    @Override
    protected MongoDatasourceDefinition createDatasourceDefinition() {
        return new DatasourceDefinition();
    }

    /**
     * Created by Gao Yuan on 2016/2/15.
     */
    public static final class DatasourceDefinition
            extends MongoDatasourceDefinitionForTest {

        @Override
        public String getDbName() {
            return "tallymockup";
        }
    }
}
