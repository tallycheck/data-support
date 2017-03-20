package com.taoswork.tallycheck.datasolution.mongo.config.beanlist;

import com.mongodb.MongoClient;
import org.mongodb.morphia.AdvancedDatastore;
import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public interface IMongoBeanList {
    public static final String DATASTORE_BEAN_NAME = "theDatastore";
    public static final String MONGOCLIENT_BEAN_NAME = "theMongoCLient";

    @Bean(name = IMongoBeanList.MONGOCLIENT_BEAN_NAME)
    MongoClient mongoClient();

    @Bean(name = IMongoBeanList.DATASTORE_BEAN_NAME)
    AdvancedDatastore datastore();
}
