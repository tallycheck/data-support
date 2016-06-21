package com.taoswork.tallycheck.testmaterial.jpa.domain.zoo;

import javax.persistence.Embeddable;

@Embeddable
public class Food {
    FoodType type;
    String name;
    String description;
}
