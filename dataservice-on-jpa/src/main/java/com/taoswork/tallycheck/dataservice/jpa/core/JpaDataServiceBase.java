package com.taoswork.tallycheck.dataservice.jpa.core;

import com.taoswork.tallycheck.dataservice.IDataServiceDefinition;
import com.taoswork.tallycheck.dataservice.jpa.config.JpaDatasourceBeanConfiguration;
import com.taoswork.tallycheck.dataservice.jpa.config.JpaDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallycheck.dataservice.service.impl.DataServiceBase;
import com.taoswork.tallycheck.general.extension.collections.ListBuilder;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class JpaDataServiceBase<
        DSrcBeanConf extends JpaDatasourceBeanConfiguration,
        DSrcConf extends JpaDatasourceConfiguration,
        JpaDbConf extends IDbConfig>
        extends DataServiceBase {

    /**
     * @param dsDef           : defines the service's name, message path, properties
     * @param dSrcBeanConfClz : defines the datastore beans which are directly used by application
     * @param dSrcConfClz     : defines the connection parameter to the database.
     * @param dbConf          : defines the db type, and its data source creation method
     */
    public JpaDataServiceBase(IDataServiceDefinition dsDef,
                              Class<? extends DSrcBeanConf> dSrcBeanConfClz,
                              Class<? extends DSrcConf> dSrcConfClz,
                              Class<? extends JpaDbConf> dbConf) {
        super(dsDef, new ListBuilder<Class>()
                .append(dSrcBeanConfClz)
                .append(dSrcConfClz)
                .append(dbConf));
    }
}
