package com.taoswork.tallycheck.info.descriptor.tab;

import com.taoswork.tallycheck.info.descriptor.base.NamedOrderedInfo;
import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedInfoImpl;
import com.taoswork.tallycheck.info.descriptor.group.IGroupInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class TabInfoImpl extends NamedOrderedInfoImpl implements ITabInfo {
    /**
     * groups are ordered
     */
    private final List<IGroupInfo> groups;

    public TabInfoImpl(List<IGroupInfo> groups) {
        this.groups = NamedOrderedInfo.NameSorter.makeObjectOrdered(groups);
    }

    @Override
    public List<IGroupInfo> getGroups() {
        return groups;
    }
}
