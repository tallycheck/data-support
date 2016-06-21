package com.taoswork.tallycheck.testmaterial.jpa.domain.business;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;

import java.util.Collection;

public interface IProject extends Persistable {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Collection<IEmployee> getEmployees();

    void setEmployees(Collection<IEmployee> employees);
}
