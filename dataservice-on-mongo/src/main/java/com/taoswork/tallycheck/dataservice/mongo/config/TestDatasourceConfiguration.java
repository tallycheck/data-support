package com.taoswork.tallycheck.dataservice.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinitionBase;
import com.taoswork.tallycheck.dataservice.mongo.MongoDatasourceDefinitionForTest;
import com.taoswork.tallycheck.general.solution.conf.TallycheckConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
@Configuration
public class TestDatasourceConfiguration extends MongoDatasourceConfiguration {
    @Override
    protected MongoDatasourceDefinition createDatasourceDefinition() {
        return new DatasourceDefinition();
    }

    /**
     * Created by Gao Yuan on 2016/2/26.
     */
    public static class DatasourceDefinition
            extends MongoDatasourceDefinitionForTest {

        @Override
        public String getDbName() {
            return "tally-test";
        }
    }
}
