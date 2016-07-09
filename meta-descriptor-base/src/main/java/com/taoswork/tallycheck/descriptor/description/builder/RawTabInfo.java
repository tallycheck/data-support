package com.taoswork.tallycheck.descriptor.description.builder;

import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedInfoRW;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawTabInfo extends NamedOrderedInfoRW, Serializable {

    void addGroup(RawGroupInfo groupInfoByComp);

    RawGroupInfo getGroup(String groupName);

    Collection<? extends RawGroupInfo> getGroups();
}
