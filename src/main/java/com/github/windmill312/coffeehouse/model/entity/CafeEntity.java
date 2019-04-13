package com.github.windmill312.coffeehouse.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cafe", schema = "coffeehouse")
public class CafeEntity {

    private Integer id;
    private UUID cafeUid = UUID.randomUUID();
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private UUID ownerUid;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
            schema = "coffeehouse", name = "coffeehouse.cafe_id_seq",
            sequenceName = "coffeehouse.cafe_id_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "coffeehouse.cafe_id_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "cafe_uid")
    public UUID getCafeUid() {
        return cafeUid;
    }

    public CafeEntity setCafeUid(UUID cafeUid) {
        this.cafeUid = cafeUid;
        return this;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public CafeEntity setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public CafeEntity setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 60)
    public String getName() {
        return name;
    }

    public CafeEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Basic
    @Column(name = "description", length = 200)
    public String getDescription() {
        return description;
    }

    public CafeEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Basic
    @Type(type = "pg-uuid")
    @Column(name = "owner_uid")
    public UUID getOwnerUid() {
        return ownerUid;
    }

    public CafeEntity setOwnerUid(UUID ownerUid) {
        this.ownerUid = ownerUid;
        return this;
    }

    public CafeEntity copy(CafeEntity entity) {
        this.setName(entity.getName());
        this.setCafeUid(entity.getCafeUid());
        this.setDescription(entity.getDescription());
        this.setLatitude(entity.getLatitude());
        this.setLongitude(entity.getLongitude());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
