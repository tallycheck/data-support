package com.taoswork.tallycheck.testmaterial.jpa.domain.common;

import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Address {
    @PresentationField(visibility = Visibility.GRID_HIDE)
    private String street;

    private String city;

    private String state;

    @Column(name = "ZIP_CODE")
    private String zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}