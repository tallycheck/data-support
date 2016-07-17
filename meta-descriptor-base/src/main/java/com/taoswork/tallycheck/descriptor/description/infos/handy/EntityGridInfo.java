package com.taoswork.tallycheck.descriptor.description.infos.handy;

import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.info.IEntityInfo;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public final class EntityGridInfo extends _BaseEntityHandyInfo {
    private String idField = "id";
    private String nameField = "name";
    public String primarySearchField = "name";
    public List<IFieldInfo> fields;

    public EntityGridInfo() {
        super();
    }

    public EntityGridInfo(EntityInfo entityInfo) {
        super(entityInfo);
        idField = entityInfo.getIdField();
        nameField = entityInfo.getNameField();
        primarySearchField = entityInfo.getPrimarySearchField();
        fields = new ArrayList<IFieldInfo>();
        Map<String, IFieldInfo> fieldMap = entityInfo.getFields();
        for (String gridField : entityInfo.getGridFields()) {
            IFieldInfo field = fieldMap.get(gridField);
            fields.add(field);
        }
    }

    @Override
    public String getInfoType() {
        return EntityInfoType.Grid.getType();
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
        return null;
    }
}
