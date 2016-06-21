package com.taoswork.tallycheck.dataservice.jpa.core.dao.query.criteria.predicate;

import com.taoswork.tallycheck.dataservice.core.dao.query.criteria.desc.LongRange;

/**
 * Created by Gao Yuan on 2015/11/3.
 */
public class LongRPredicateProvider extends _SupportRangePredicateProvider<Long, Long, LongRange> {
    @Override
    protected Long numberTypeToDateType(Long number) {
        return number;
    }
}
