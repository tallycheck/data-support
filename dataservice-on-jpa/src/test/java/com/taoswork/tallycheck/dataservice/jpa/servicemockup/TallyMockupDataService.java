package com.taoswork.tallycheck.dataservice.jpa.servicemockup;

import com.taoswork.tallycheck.dataservice.annotations.DataService;
import com.taoswork.tallycheck.dataservice.jpa.config.JpaDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallycheck.dataservice.jpa.config.db.TestDbConfig;
import com.taoswork.tallycheck.dataservice.jpa.core.JpaDataServiceBase;
import com.taoswork.tallycheck.dataservice.jpa.servicemockup.datasource.TallyMockupJpaDatasourceBeanConfiguration;
import com.taoswork.tallycheck.dataservice.jpa.servicemockup.datasource.TallyMockupJpaDatasourceConfiguration;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataService
public class TallyMockupDataService
        extends JpaDataServiceBase<TallyMockupJpaDatasourceBeanConfiguration,
        JpaDatasourceConfiguration,
        IDbConfig> {
    public static final String COMPONENT_NAME = TallyMockupDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyMockupDataService(Class<? extends JpaDatasourceConfiguration> dSrcConfClz,
                                  Class<? extends IDbConfig> dbConf) {
        super(new TallyMockupDataServiceDefinition(), TallyMockupJpaDatasourceBeanConfiguration.class,
                dSrcConfClz, dbConf);
    }

    public TallyMockupDataService(Class<? extends IDbConfig> dbConf) {
        this(TallyMockupJpaDatasourceConfiguration.class, dbConf);
    }

    public TallyMockupDataService() {
        this(TestDbConfig.class);
    }
}