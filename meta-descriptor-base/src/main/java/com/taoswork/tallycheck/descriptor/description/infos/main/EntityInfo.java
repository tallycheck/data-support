package com.taoswork.tallycheck.descriptor.description.infos.main;

import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.info.IEntityInfo;
import com.taoswork.tallycheck.info.descriptor.base.Named;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;
import com.taoswork.tallycheck.info.descriptor.tab.ITabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface EntityInfo extends Named, IEntityInfo {

    String getPrimarySearchField();

    IFieldInfo getField(String fieldName);

    Map<String, IFieldInfo> getFields();

    List<ITabInfo> getTabs();

    List<String> getGridFields();

    Map<String, EntityInfo> getReferencingInfos();

    Map<String, IEntityInfo> getReferencingInfosAsType(EntityInfoType entityInfoType);
}
