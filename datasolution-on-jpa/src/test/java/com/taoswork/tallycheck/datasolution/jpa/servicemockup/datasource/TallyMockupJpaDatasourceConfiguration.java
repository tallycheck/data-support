package com.taoswork.tallycheck.datasolution.jpa.servicemockup.datasource;

import com.taoswork.tallycheck.datasolution.jpa.JpaDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.jpa.config.JpaDatasourceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class TallyMockupJpaDatasourceConfiguration extends JpaDatasourceConfiguration {
    @Override
    protected JpaDatasourceDefinition createDatasourceDefinition() {
        return new TallyMockupJpaDatasourceDefinition();
    }
}
