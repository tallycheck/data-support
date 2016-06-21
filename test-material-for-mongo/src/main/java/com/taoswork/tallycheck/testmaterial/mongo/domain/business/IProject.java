package com.taoswork.tallycheck.testmaterial.mongo.domain.business;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;

import java.util.Collection;

public interface IProject extends PersistableDocument {

    String getName();

    void setName(String name);

    Collection<IEmployee> getEmployees();

    void setEmployees(Collection<IEmployee> employees);
}
