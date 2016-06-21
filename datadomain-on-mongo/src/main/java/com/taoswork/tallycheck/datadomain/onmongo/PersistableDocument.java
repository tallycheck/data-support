package com.taoswork.tallycheck.datadomain.onmongo;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/02/8.
 */
public interface PersistableDocument extends Persistable {
    ObjectId getId();

    void setId(ObjectId id);
}
