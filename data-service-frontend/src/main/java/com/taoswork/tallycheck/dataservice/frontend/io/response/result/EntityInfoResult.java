package com.taoswork.tallycheck.dataservice.frontend.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.info.IEntityInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityInfoResult {

    private final BasicInfo basic = new BasicInfo();

    private Map<String, IEntityInfo> details;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BasicInfo getBasic() {
        return basic;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getDetails() {
        if (null == details) {
            return null;
        }
        return Collections.unmodifiableMap(details);
    }

    public EntityInfoResult setDetails(Map<String, IEntityInfo> details) {
        this.details = details;
        return this;
    }


    public EntityInfoResult addDetail(String typeName, IEntityInfo entityDetail) {
        if (this.details == null) {
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.put(typeName, entityDetail);
        return this;
    }

    public EntityInfoResult addDetails(Map<String, IEntityInfo> entityDetailMap) {
        if (this.details == null) {
            this.details = new HashMap<String, IEntityInfo>();
        }
        this.details.putAll(entityDetailMap);
        return this;
    }

    public <T extends IEntityInfo> T getDetail(String infoType) {
        return (T) details.get(infoType);
    }

    public <T extends IEntityInfo> T getDetail(EntityInfoType infoType) {
        return (T) details.get(infoType.getType());
    }

}
