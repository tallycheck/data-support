package com.taoswork.tallycheck.datasolution.mongo.core.query.criteria.converter;

import com.taoswork.tallycheck.datasolution.core.dao.query.criteria.converter.FilterValueConverter;

public class StringLikeFilterValueConverter implements FilterValueConverter<String> {

    @Override
    public String convert(Class type, String stringValue) {
        if(null == stringValue)
            return null;
        return stringValue.toLowerCase();
    }

}
