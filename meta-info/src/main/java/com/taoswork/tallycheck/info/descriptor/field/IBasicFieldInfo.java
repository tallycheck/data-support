package com.taoswork.tallycheck.info.descriptor.field;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public interface IBasicFieldInfo extends IFieldInfo {

    boolean isSupportSort();

    boolean isSupportFilter();

    boolean isGridVisible();
}
