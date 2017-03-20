package com.taoswork.tallycheck.datasolution.mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.taoswork.tallycheck.general.solution.conf.TallycheckConfiguration;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class MongoDatasourceDefinitionBase implements MongoDatasourceDefinition{
    private final ServerAddress serverAddress;

    public MongoDatasourceDefinitionBase() {
        serverAddress = determineServerAddress();
    }

    protected ServerAddress determineServerAddress() {
        org.apache.commons.configuration2.Configuration conf = TallycheckConfiguration.instance();
        String host = conf.getString("tallycheck.db.mongo.host", ServerAddress.defaultHost());
        int port = conf.getInt("tallycheck.db.mongo.port", ServerAddress.defaultPort());
        return new ServerAddress(host, port);
    }

    @Override
    public final ServerAddress getServerAddress() {
        return serverAddress;
    }

    @Override
    public Class getRootDataClass() {
        return null;
    }
}
