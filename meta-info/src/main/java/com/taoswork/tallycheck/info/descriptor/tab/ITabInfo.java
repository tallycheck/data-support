package com.taoswork.tallycheck.info.descriptor.tab;

import com.taoswork.tallycheck.info.descriptor.base.NamedOrdered;
import com.taoswork.tallycheck.info.descriptor.group.IGroupInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface ITabInfo extends NamedOrdered {
    List<IGroupInfo> getGroups();
}
