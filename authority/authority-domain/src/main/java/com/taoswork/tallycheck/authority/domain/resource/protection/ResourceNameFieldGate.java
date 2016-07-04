package com.taoswork.tallycheck.authority.domain.resource.protection;

import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;
import com.taoswork.tallycheck.datadomain.base.entity.valuegate.BaseFieldGate;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class ResourceNameFieldGate extends BaseFieldGate<String> {
    @Override
    protected String doStore(String val, String oldVal) {
        return ResourceUtility.unifiedResourceName(val);
    }
}
