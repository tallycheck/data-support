package com.taoswork.tallycheck.dataservice.jpa.core.dao.query.criteria.converter;

import com.taoswork.tallycheck.dataservice.core.dao.query.criteria.converter.FilterValueConverter;
import com.taoswork.tallycheck.general.solution.property.RuntimePropertiesPublisher;

public class StringLikeFilterValueConverter implements FilterValueConverter<String> {

    protected boolean getOnlyStartsWith() {
        return RuntimePropertiesPublisher.instance().getBoolean("jpa.query.for.string.like.only.search.starting", false);
    }

    @Override
    public String convert(Class type, String stringValue) {
        return getOnlyStartsWith() ? stringValue.toLowerCase() + "%" : "%" + stringValue.toLowerCase() + "%";
    }

}
