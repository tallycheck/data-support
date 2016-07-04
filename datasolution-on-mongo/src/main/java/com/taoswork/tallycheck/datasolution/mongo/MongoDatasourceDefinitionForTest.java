package com.taoswork.tallycheck.datasolution.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.taoswork.tallycheck.general.solution.conf.TallycheckConfiguration;

/**
 * Created by Gao Yuan on 2016/6/26.
 */
public abstract class MongoDatasourceDefinitionForTest extends MongoDatasourceDefinitionBase {
    @Override
    protected ServerAddress determineServerAddress() {
        org.apache.commons.configuration2.Configuration conf = TallycheckConfiguration.instance();
        String host = conf.getString("test.db.mongo.host", ServerAddress.defaultHost());
        int port = conf.getInt("test.db.mongo.port", ServerAddress.defaultPort());
        return new ServerAddress(host, port);
    }

    public void dropDatabase() {
        MongoDatasourceDefinitionForTest def = this;
        final MongoClient mongo = new MongoClient(def.getServerAddress());
        final MongoDatabase db = mongo.getDatabase(def.getDbName());
        db.drop();
    }

}
