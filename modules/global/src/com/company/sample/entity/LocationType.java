package com.company.sample.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum LocationType implements EnumClass<String> {

    HOTEL("hotel"),
    HOSTEL("hostel"),
    ROOM("room"),
    APARTMENTS("apartments"),
    COUNTRY_HOUSE("country_house");

    private String id;

    LocationType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static LocationType fromId(String id) {
        for (LocationType at : LocationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}