package com.taoswork.tallycheck.datasolution.mongo.config;

import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.mongo.MongoDatasourceDefinitionForTest;
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
