package com.taoswork.tallybook.testmaterial.jpa;

import com.taoswork.tallybook.testmaterial.jpa.persistence.conf.TestDbPersistenceConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TestDbPersistenceConfig extends TestDbPersistenceConfigBase {
    public static final String TEST_DB_PU_NAME = "tallymockupPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-test.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_mockup";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
