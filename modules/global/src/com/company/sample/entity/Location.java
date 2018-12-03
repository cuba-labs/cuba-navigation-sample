package com.company.sample.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "SAMPLE_LOCATION")
@Entity(name = "sample_Location")
public class Location extends StandardEntity {
    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "TYPE", nullable = false)
    protected String type;

    @NotNull
    @Column(name = "CAPACITY", nullable = false)
    protected Integer capacity;

    @Column(name = "STARS")
    protected Integer stars;

    public LocationType getType() {
        return type == null ? null : LocationType.fromId(type);
    }

    public void setType(LocationType type) {
        this.type = type == null ? null : type.getId();
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}