package com.taoswork.tallycheck.authority.solution.engine;

import com.taoswork.tallycheck.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallycheck.authority.solution.domain.resource.Protection;
import com.taoswork.tallycheck.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallycheck.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallycheck.dataservice.mongo.core.entityservice.MongoEntityService;
import redis.clients.jedis.Jedis;

/**
 * Created by Gao Yuan on 2016/2/28.
 */
public class FastPermissionEngine
        extends PermissionEngine {
    
    private Jedis jedis = new Jedis("localhost");

    public FastPermissionEngine(MongoEntityService entityService, Class<? extends UserAuthority> userClz, Class<? extends GroupAuthority> groupClz) {
        super(entityService, ProtectionSpace.class, Protection.class, userClz, groupClz);
    }
}