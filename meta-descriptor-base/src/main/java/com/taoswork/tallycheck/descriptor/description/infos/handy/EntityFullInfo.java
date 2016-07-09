package com.taoswork.tallycheck.descriptor.description.infos.handy;

import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.general.extension.utils.CloneUtility;
import com.taoswork.tallycheck.info.IEntityInfo;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;
import com.taoswork.tallycheck.info.descriptor.tab.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public final class EntityFullInfo extends _BaseEntityHandyInfo implements IEntityInfo {
    private final String idField;
    private final String nameField;
    public final String primarySearchField;
    public final Map<String, IFieldInfo> fields;
    public final List<ITabInfo> tabs;
    public final List<String> gridFields;
    private final Map<String, IEntityInfo> referencingEntryInfos;

    public EntityFullInfo(EntityInfo entityInfo) {
        super(entityInfo);
        this.idField = entityInfo.getIdField();
        this.nameField = entityInfo.getNameField();
        this.primarySearchField = entityInfo.getPrimarySearchField();
        this.fields = CloneUtility.makeClone(entityInfo.getFields());
        this.tabs = CloneUtility.makeClone(entityInfo.getTabs());
        this.gridFields = CloneUtility.makeClone(entityInfo.getGridFields());
        this.referencingEntryInfos = CloneUtility.makeClone(entityInfo.getReferencingInfosAsType(EntityInfoType.Grid));
    }

    @Override
    public String getInfoType() {
        return EntityInfoType.Full.getType();
    }

    @Override
    public String getIdField() {
        return idField;
    }

    @Override
    public String getNameField() {
        return nameField;
    }

    @Override
    public Map<String, IEntityInfo> getReferencing() {
        return referencingEntryInfos;
    }
}
