package com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth;

import com.taoswork.tallycheck.authority.domain.user.GroupAuthority;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/2/27.
 */
@Entity("testgroup")
@PersistEntity(value = "testgroup",
        asDefaultPermissionGuardian = true)
public class TGroupAuthority extends GroupAuthority {
}
