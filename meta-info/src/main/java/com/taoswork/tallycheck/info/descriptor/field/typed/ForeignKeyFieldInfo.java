package com.taoswork.tallycheck.info.descriptor.field.typed;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntityHelper;
import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;
import com.taoswork.tallycheck.datadomain.base.restful.EntityActionPaths;
import com.taoswork.tallycheck.info.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class ForeignKeyFieldInfo extends BasicFieldInfoBase {
    public String entityType;
    public String selectUri;
    public String recordUri;
    public String idFieldName = "id";
    public String displayFieldName = "name";

    public ForeignKeyFieldInfo() {
    }

    public ForeignKeyFieldInfo(String name, String friendlyName, boolean editable,
                               Class declareType, Class entityType,
                               String idFieldName, String displayFieldName) {
        super(name, friendlyName, editable);
        this.entityType = entityType.getName();
        String entityName = PersistEntityHelper.getEntityName(declareType);
        this.selectUri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.SELECT);
        this.recordUri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.READ);
        this.idFieldName = idFieldName;
        this.displayFieldName = displayFieldName;
    }
}
