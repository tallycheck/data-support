package com.taoswork.tallycheck.dataservice.query;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public class PropertyFilterCriteria extends PropertyCriteria {
    protected Collection<String> filterValues = new ArrayList<String>();

    public PropertyFilterCriteria(String propertyName) {
        super(propertyName);
    }

    public PropertyFilterCriteria(String propertyName, String filterValue) {
        super(propertyName);
        setFilterValue(filterValue);
    }

    public PropertyFilterCriteria(String propertyName, Collection<String> filterValues) {
        super(propertyName);
        setFilterValues(filterValues);
    }

    public PropertyFilterCriteria(String propertyName, String[] filterValues) {
        super(propertyName);
        setFilterValues(Arrays.asList(filterValues));
    }

    public PropertyFilterCriteria clearFilterValues() {
        filterValues.clear();
        return this;
    }

    public PropertyFilterCriteria setFilterValue(String value) {
        clearFilterValues();
        addFilterValue(value);
        return this;
    }

    public PropertyFilterCriteria addFilterValue(String value) {
        filterValues.add(value);
        return this;
    }

    public PropertyFilterCriteria addFilterValues(Collection<String> values) {
        for (String value : values) {
            addFilterValue(value);
        }
        return this;
    }

    public PropertyFilterCriteria setFilterValues(Collection<String> filterValues) {
        this.filterValues = filterValues;
        return this;
    }

    public Collection<String> getFilterValues() {
        return Collections.unmodifiableCollection(filterValues);
    }

    @Override
    public PropertyFilterCriteria clone() throws CloneNotSupportedException {
        PropertyFilterCriteria criteria = (PropertyFilterCriteria) super.clone();
        criteria.filterValues = new ArrayList<String>();
        criteria.filterValues.addAll(filterValues);
        return criteria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PropertyFilterCriteria)) return false;

        PropertyFilterCriteria that = (PropertyFilterCriteria) o;

        return new EqualsBuilder()
                .append(propertyName, that.propertyName)
                .append(filterValues, that.filterValues)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(propertyName)
                .append(filterValues)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FILTER: [" + propertyName + "] having '"
                + filterValues +
                '\'';
    }
}
