package com.taoswork.tallycheck.datasolution.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallycheck.datasolution.core.dao.query.criteria.desc.IntegerRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class IntRPredicateProvider extends _SupportRangePredicateProvider<Integer, Integer, IntegerRange> {
    @Override
    protected Integer numberTypeToDateType(Integer number) {
        return number;
    }
}
