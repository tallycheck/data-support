package com.taoswork.tallycheck.datasolution.jpa.servicemockup;

import com.taoswork.tallycheck.datasolution.annotations.DataSolutionMark;
import com.taoswork.tallycheck.datasolution.jpa.config.JpaDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.jpa.config.db.IDbConfig;
import com.taoswork.tallycheck.datasolution.jpa.config.db.TestDbConfig;
import com.taoswork.tallycheck.datasolution.jpa.core.JpaDataSolutionBase;
import com.taoswork.tallycheck.datasolution.jpa.servicemockup.datasource.TallyMockupJpaDatasourceBeanConfiguration;
import com.taoswork.tallycheck.datasolution.jpa.servicemockup.datasource.TallyMockupJpaDatasourceConfiguration;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@DataSolutionMark
public class TallyMockupDataSolution
        extends JpaDataSolutionBase<TallyMockupJpaDatasourceBeanConfiguration,
                JpaDatasourceConfiguration,
                IDbConfig> {
    public static final String COMPONENT_NAME = TallyMockupDataSolutionDefinition.DATA_SOLUTION_NAME;

    public TallyMockupDataSolution(Class<? extends JpaDatasourceConfiguration> dSrcConfClz,
                                   Class<? extends IDbConfig> dbConf) {
        super(new TallyMockupDataSolutionDefinition(), TallyMockupJpaDatasourceBeanConfiguration.class,
                dSrcConfClz, dbConf);
    }

    public TallyMockupDataSolution(Class<? extends IDbConfig> dbConf) {
        this(TallyMockupJpaDatasourceConfiguration.class, dbConf);
    }

    public TallyMockupDataSolution() {
        this(TestDbConfig.class);
    }
}
