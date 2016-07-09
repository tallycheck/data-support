package com.taoswork.tallycheck.datasolution.mongo.core;

import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.IDataSolutionDefinition;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoDatasourceBeanConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoSolutionConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.datasolution.mongo.core.script.DataScript;
import com.taoswork.tallycheck.datasolution.mongo.core.script.MongoDataImporter;
import com.taoswork.tallycheck.datasolution.service.impl.DataSolutionBase;
import com.taoswork.tallycheck.general.extension.collections.ListBuilder;
import com.taoswork.tallycheck.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.AdvancedDatastore;

import java.util.List;
import java.util.Properties;


/**
 * Created by Gao Yuan on 2016/2/16.
 */

public abstract class MongoDataSolutionBase<
        EntityTypeConf extends MongoPersistableConfiguration,
        DSrcConf extends MongoDatasourceConfiguration>
        extends DataSolutionBase {

    /**
     * @param dSrvDef        : defines the service's name, message path, properties
     * @param entityTypeConf : defines the entity types
     * @param dSrcConf       : defines the connection parameter to the database.
     */
    public MongoDataSolutionBase(IDataSolutionDefinition dSrvDef,
                                 Class<? extends EntityTypeConf> entityTypeConf,
                                 Class<? extends DSrcConf> dSrcConf, Class... confs) {
        super(dSrvDef, MongoSolutionConfiguration.class, new ListBuilder<Class>()
                .append(MongoDatasourceBeanConfiguration.class)
                .append(entityTypeConf).append(dSrcConf).append(confs));
    }

    @Override
    protected void postConstruct() {
        super.postConstruct();
        MongoEntityService entityService = this.getService(MongoEntityService.COMPONENT_NAME);
        AdvancedDatastore datastore = entityService.getAdvancedDatastore();

        RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer = this.getService(IDataSolution.PROPERTY_CONFIGURER);
        Properties props = runtimeEnvironmentPropertyPlaceholderConfigurer.getSubProperties("tallydataservice");
        String importFiles = props.getProperty("tallydataservice.import");
        if(StringUtils.isNotEmpty(importFiles)){
            MongoDataImporter importer = new MongoDataImporter();
            List<DataScript> dss = DataScript.parseDataScripts(importFiles);
            for (DataScript ds : dss){
                importer.load(datastore, ds.getTask(), ds.getFile());
            }
        }
    }
}
