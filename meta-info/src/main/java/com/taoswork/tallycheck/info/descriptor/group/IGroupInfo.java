package com.taoswork.tallycheck.info.descriptor.group;

import com.taoswork.tallycheck.info.descriptor.base.NamedOrderedInfo;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface IGroupInfo extends NamedOrderedInfo {
    List<String> getFields();
}
