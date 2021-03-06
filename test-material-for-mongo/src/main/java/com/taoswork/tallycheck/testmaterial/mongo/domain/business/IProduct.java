package com.taoswork.tallycheck.testmaterial.mongo.domain.business;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;

public interface IProduct extends PersistableDocument {

    String getName();

    void setName(String name);

    Double getPrice();

    void setPrice(Double price);
}
