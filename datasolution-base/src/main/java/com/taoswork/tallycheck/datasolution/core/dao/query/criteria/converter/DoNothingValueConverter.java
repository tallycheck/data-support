package com.taoswork.tallycheck.datasolution.core.dao.query.criteria.converter;

public class DoNothingValueConverter implements FilterValueConverter<String> {

    @Override
    public String convert(Class type, String stringValue) {
        return stringValue;
    }

}
