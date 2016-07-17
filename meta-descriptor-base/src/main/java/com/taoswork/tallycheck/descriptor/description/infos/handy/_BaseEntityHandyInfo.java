package com.taoswork.tallycheck.descriptor.description.infos.handy;

import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.info.IEntityInfo;
import com.taoswork.tallycheck.info.descriptor.base.impl.NamedImpl;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
abstract class _BaseEntityHandyInfo extends NamedImpl implements IEntityInfo {
    private boolean containsHierarchy = false;
    private String entityType = "";

    protected _BaseEntityHandyInfo() {
    }

    protected _BaseEntityHandyInfo(EntityInfo entityInfo) {
        this.copyNamedInfo(entityInfo);
        this.containsHierarchy = entityInfo.isWithHierarchy();
        this.entityType = entityInfo.getType();
    }

    @Override
    public boolean isWithHierarchy() {
        return containsHierarchy;
    }

    @Override
    public String getType() {
        return entityType;
    }
}
