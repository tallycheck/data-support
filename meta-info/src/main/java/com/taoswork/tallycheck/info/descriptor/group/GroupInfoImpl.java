package com.taoswork.tallycheck.info.descriptor.group;

import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public class GroupInfoImpl extends NamedOrderedImpl implements IGroupInfo {
    /**
     * The fields are ordered
     */
    private List<String> fields;

    public GroupInfoImpl() {
    }

    public GroupInfoImpl(List<String> fields) {
        this.fields = new ArrayList<String>(fields);
    }

    @Override
    public List<String> getFields() {
        return fields;
    }
}
