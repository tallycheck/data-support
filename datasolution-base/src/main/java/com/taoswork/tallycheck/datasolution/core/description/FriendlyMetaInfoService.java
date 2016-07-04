package com.taoswork.tallycheck.datasolution.core.description;

import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface FriendlyMetaInfoService {
    public static final String SERVICE_NAME = "FriendlyMetaInfoService";
    public static final String MESSAGE_SOURCE_BEAN_NAME = "FriendlyMetaInfoServiceMessageSource";

    EntityInfo makeFriendly(EntityInfo rawEntityInfo, Locale locale);
}
