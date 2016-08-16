package com.taoswork.tallycheck.dataservice.frontend.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallycheck.authority.core.UnexpectedException;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.handy.EntityFormInfo;
import com.taoswork.tallycheck.descriptor.description.infos.handy.EntityFullInfo;
import com.taoswork.tallycheck.descriptor.description.infos.handy.EntityGridInfo;
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
    private IEntityInfo full = null;
    private IEntityInfo form = null;
    private IEntityInfo grid = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BasicInfo getBasic() {
        return basic;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public IEntityInfo getFull() {
        return full;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public IEntityInfo getForm() {
        return form;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public IEntityInfo getGrid() {
        return grid;
    }
//
//    public EntityInfoResult setDetails(Map<String, IEntityInfo> details) {
//        this.details = details;
//        return this;
//    }


    public EntityInfoResult addDetail(IEntityInfo info) {
        if (info == null)
            return this;
        if (info instanceof EntityFullInfo) {
            this.full = info;
        } else if (info instanceof EntityFormInfo) {
            this.form = info;
        } else if (info instanceof EntityGridInfo) {
            this.grid = info;
        } else {
            throw new UnexpectedException();
        }
        return this;
    }

}
