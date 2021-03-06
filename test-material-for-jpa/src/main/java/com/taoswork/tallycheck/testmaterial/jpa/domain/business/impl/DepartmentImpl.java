package com.taoswork.tallycheck.testmaterial.jpa.domain.business.impl;

import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.testmaterial.jpa.domain.TallyMockupDataDomain;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.IDepartment;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.IEmployee;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.embed.EmployeeName;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.embed.EmployeeNameX;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "DEPT")
public class DepartmentImpl implements IDepartment {
    protected static final String ID_GENERATOR_NAME = "DepartmentImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table = TallyMockupDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 0)
    @Column(name = "ID")
    private Long id;

    private String name;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    private Collection<IEmployee> employees;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @OrderBy("cube ASC")
    private List<IEmployee> employeesList;

//
//    @OneToMany(mappedBy="department")
//    @OrderColumn
//    private EmployeeImpl[] employeesArray;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @MapKeyColumn(name = "CUB_ID")
    private Map<String, IEmployee> employeesByCubicle;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @MapKey(name = "id")
    private Map<Integer, IEmployee> employeesMap;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    private Map<EmployeeName, IEmployee> employeesByName;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @MapField(mode = MapMode.Entity, keyFieldOnValue = "nameX")
    private Map<EmployeeNameX, IEmployee> employeesByNameX;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @MapKey//default is to use the identifier attribute
    private Map employeesByUnTypedId;

    @OneToMany(targetEntity = EmployeeImpl.class, mappedBy = "department")
    @MapKey(name = "name")
    private Map employeesByUnTypedName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<IEmployee> getEmployees() {
        return employees;
    }

    @Override
    public void setEmployees(Collection<IEmployee> employees) {
        this.employees = employees;
    }

    @Override
    public List<IEmployee> getEmployeesList() {
        return employeesList;
    }

    @Override
    public void setEmployeesList(List<IEmployee> employeesList) {
        this.employeesList = employeesList;
    }

    @Override
    public Map<String, IEmployee> getEmployeesByCubicle() {
        return employeesByCubicle;
    }

    @Override
    public void setEmployeesByCubicle(Map<String, IEmployee> employeesByCubicle) {
        this.employeesByCubicle = employeesByCubicle;
    }

    @Override
    public Map<Integer, IEmployee> getEmployeesMap() {
        return employeesMap;
    }

    @Override
    public void setEmployeesMap(Map<Integer, IEmployee> employeesMap) {
        this.employeesMap = employeesMap;
    }

    @Override
    public Map<EmployeeName, IEmployee> getEmployeesByName() {
        return employeesByName;
    }

    @Override
    public void setEmployeesByName(Map<EmployeeName, IEmployee> employeesByName) {
        this.employeesByName = employeesByName;
    }

    @Override
    public Map<EmployeeNameX, IEmployee> getEmployeesByNameX() {
        return employeesByNameX;
    }

    @Override
    public void setEmployeesByNameX(Map<EmployeeNameX, IEmployee> employeesByNameX) {
        this.employeesByNameX = employeesByNameX;
    }

    @Override
    public Map getEmployeesByUnTypedId() {
        return employeesByUnTypedId;
    }

    @Override
    public void setEmployeesByUnTypedId(Map employeesByUnTypedId) {
        this.employeesByUnTypedId = employeesByUnTypedId;
    }

    @Override
    public Map getEmployeesByUnTypedName() {
        return employeesByUnTypedName;
    }

    @Override
    public void setEmployeesByUnTypedName(Map employeesByUnTypedName) {
        this.employeesByUnTypedName = employeesByUnTypedName;
    }

    @Override
    public Object getInstanceId() {
        return getId();
    }

    @Override
    public String getInstanceName() {
        return getName();
    }
}
