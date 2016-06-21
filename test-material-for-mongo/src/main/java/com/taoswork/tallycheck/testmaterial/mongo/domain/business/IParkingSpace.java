package com.taoswork.tallycheck.testmaterial.mongo.domain.business;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;

public interface IParkingSpace extends PersistableDocument {

    int getLot();

    void setLot(int lot);

    String getLocation();

    void setLocation(String location);

    IEmployee getEmployee();

    void setEmployee(IEmployee employee);

    Object getEmployeeObj();

    void setEmployeeObj(Object employeeObj);

    IEmployee getEmployeeImpl();

    void setEmployeeImpl(IEmployee employeeImpl);
}
