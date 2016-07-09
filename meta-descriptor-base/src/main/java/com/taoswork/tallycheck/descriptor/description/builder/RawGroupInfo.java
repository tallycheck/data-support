package com.taoswork.tallycheck.descriptor.description.builder;

import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedInfoRW;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
interface RawGroupInfo extends NamedOrderedInfoRW, Serializable {

    void addField(String field);

    void setFields(Collection<String> fields);

    Collection<String> getFields();

    void clearFields();

}
