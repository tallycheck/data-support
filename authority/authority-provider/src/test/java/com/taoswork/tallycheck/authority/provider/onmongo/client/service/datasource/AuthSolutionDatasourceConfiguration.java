package com.taoswork.tallycheck.authority.provider.onmongo.client.service.datasource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinitionForTest;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoDatasourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class AuthSolutionDatasourceConfiguration extends MongoDatasourceConfiguration {
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
            return "auth-test";
        }

        public void dropDatabase() {
            DatasourceDefinition def = this;
            final MongoClient mongo = new MongoClient(def.getServerAddress());
            final MongoDatabase db = mongo.getDatabase(def.getDbName());
            db.drop();
        }
    }
}
