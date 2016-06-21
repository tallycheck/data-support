package com.taoswork.tallycheck.testmaterial.jpa.domain.zoo;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */

@PersistEntity(permissionGuardian = Fish.class)
public interface Fish extends Animal {
}
