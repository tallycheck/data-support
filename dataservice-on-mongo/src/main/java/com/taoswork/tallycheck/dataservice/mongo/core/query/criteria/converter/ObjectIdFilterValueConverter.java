package com.taoswork.tallycheck.dataservice.mongo.core.query.criteria.converter;

import com.taoswork.tallycheck.dataservice.core.dao.query.criteria.converter.FilterValueConverter;
import org.bson.types.ObjectId;

public class ObjectIdFilterValueConverter implements FilterValueConverter<ObjectId> {

    @Override
    public ObjectId convert(Class type, String stringValue) {
        ObjectId objectId = new ObjectId(stringValue);
        return objectId;
    }

}
