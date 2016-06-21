package com.taoswork.tallycheck.testmaterial.mongo.domain.zoo;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Food {
    FoodType type;
    String name;
    String description;
}
