package com.taoswork.tallycheck.testmaterial.jpa.domain.business;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.impl.EmployeeImpl;

public interface IParkingSpace extends Persistable {
    int getId();

    void setId(int id);

    int getLot();

    void setLot(int lot);

    String getLocation();

    void setLocation(String location);

    IEmployee getEmployee();

    void setEmployee(IEmployee employee);

    Object getEmployeeObj();

    void setEmployeeObj(Object employeeObj);

    EmployeeImpl getEmployeeImpl();

    void setEmployeeImpl(EmployeeImpl employeeImpl);
}
