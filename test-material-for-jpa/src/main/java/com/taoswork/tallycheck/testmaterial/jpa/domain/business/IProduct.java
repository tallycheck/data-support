package com.taoswork.tallycheck.testmaterial.jpa.domain.business;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;

public interface IProduct extends Persistable {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Double getPrice();

    void setPrice(Double price);
}
