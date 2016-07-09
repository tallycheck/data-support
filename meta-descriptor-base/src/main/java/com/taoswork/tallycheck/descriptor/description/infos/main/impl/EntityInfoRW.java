package com.taoswork.tallycheck.descriptor.description.infos.main.impl;

import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.info.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;
import com.taoswork.tallycheck.info.descriptor.tab.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfoRW extends EntityInfo, NamedInfoRW {
    void setIdField(String idField);

    void setNameField(String nameField);

    void setPrimarySearchField(String primarySearchField);

    void setFields(Map<String, IFieldInfo> fields);

    void setTabs(List<ITabInfo> tabs);

    void setGridFields(List<String> gridFields);

    void addReferencingInfo(String entityName, EntityInfo info);
}
