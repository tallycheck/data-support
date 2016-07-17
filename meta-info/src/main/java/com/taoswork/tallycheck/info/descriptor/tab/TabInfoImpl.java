package com.taoswork.tallycheck.info.descriptor.tab;

import com.taoswork.tallycheck.info.descriptor.base.NamedOrdered;
import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedImpl;
import com.taoswork.tallycheck.info.descriptor.group.IGroupInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class TabInfoImpl extends NamedOrderedImpl implements ITabInfo {
    /**
     * groups are ordered
     */
    private List<IGroupInfo> groups;

    public TabInfoImpl() {
    }

    public TabInfoImpl(List<IGroupInfo> groups) {
        this.groups = NamedOrdered.NameSorter.makeObjectOrdered(groups);
    }

    @Override
    public List<IGroupInfo> getGroups() {
        return groups;
    }
}
