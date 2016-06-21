package com.taoswork.tallycheck.dataservice.mongo.core.query.criteria.predicate;

import com.taoswork.tallycheck.dataservice.core.dao.query.criteria.desc.IntegerRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class IntRPredicateProvider extends BaseRangePredicateProvider<Integer, Integer, IntegerRange> {
    @Override
    protected Integer numberTypeToDateType(Integer number) {
        return number;
    }
}
