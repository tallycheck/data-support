package com.taoswork.tallycheck.testmaterial.general.domain.meta;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
@PersistEntity(
        fieldOverrides = {@PersistEntity.FieldOverride(fieldName = "aa2", define = @PersistField(length = 2))})
public class AAB extends AA {
    public String aab;

    public String getAab() {
        return aab;
    }

    public void setAab(String aaa) {
        this.aab = aaa;
    }
}
