package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.dataservice.io.request.*;
import com.taoswork.tallycheck.dataservice.io.response.*;

import java.util.Collection;

/**
 * Created by gaoyuan on 6/29/16.
 */
public interface IDataService {

    String getName();

    Collection<EntityType> getEntityTypes();

    NewInstanceResponse newInstance(NewInstanceRequest request);

    CreateResponse create(CreateRequest request);

    ReadResponse read(ReadRequest request);

    UpdateResponse update(UpdateRequest request);

    UpdateFieldResponse update(UpdateFieldRequest request);

    DeleteResponse delete(DeleteRequest request);

    QueryResponse query(QueryRequest request);

    InfoResponse info (InfoRequest request);
}
