package com.taoswork.tallycheck.authority.provider.onnothing.client;

import com.taoswork.tallycheck.authority.client.filter.BaseFilter;
import com.taoswork.tallycheck.authority.provider.onnothing.common.NativeDoc;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.GuardedDocInstance;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class ByTagFilter extends BaseFilter {
    public final static String TYPE_NAME = "by_tag";

    private String tag;
    @Override
    protected void doAnalyzeFilterParameter(String parameter) {
        tag = this.getFilterParameter();
    }

    @Override
    public boolean isMatch(Object instance) {
        NativeDoc doc = ((GuardedDocInstance) instance).getDomainObject();
        if (doc == null)
            return false;
        return doc.hasTag(tag);
    }
}
