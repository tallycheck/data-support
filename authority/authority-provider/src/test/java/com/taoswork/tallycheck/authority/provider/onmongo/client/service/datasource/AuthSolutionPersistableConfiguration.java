package com.taoswork.tallycheck.authority.provider.onmongo.client.service.datasource;


import com.taoswork.tallycheck.authority.domain.AuthorityDomain;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TGroupAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TUserAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoPersistableConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
@Configuration
public class AuthSolutionPersistableConfiguration
        extends MongoPersistableConfiguration {

    @Override
    protected Class<?>[] createPersistableEntities() {
        List<Class> classes = new ArrayList<Class>();
        CollectionUtils.addAll(classes, AuthorityDomain.domainEntities());
        classes.add(TGroupAuthority.class);
        classes.add(TUserAuthority.class);
        classes.add(XFile.class);
        classes.add(CS0File.class);
        classes.add(CS1File.class);
        classes.add(CM0File.class);
        classes.add(CM1File.class);
        classes.add(CCFile.class);
        return classes.toArray(new Class[]{});
    }
}
