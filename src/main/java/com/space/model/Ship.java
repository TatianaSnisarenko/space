package com.space.model;

import jakarta.validation.constraints.*;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The field 'name' must not be empty")
    @Size(max = 50, message = "The field's 'name' max size must be not bigger than 50 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "The field 'planet' must not be empty")
    @Size(max = 50, message = "The field's 'planet' max size must be not bigger than 50 characters")
    @Column(name = "planet")
    private String planet;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "shipType")
    private ShipType shipType;

    @NotNull(message = "The field 'prodDate' must not be null")
    @Column(name = "prodDate")
    @Temporal(TemporalType.DATE)
    private Date prodDate;

    @NotNull
    @Column(name = "isUsed")
    private Boolean isUsed;

    @NotNull(message = "The field 'speed' must not be null")
    @DecimalMin(value = "0.01", message = "The field 'speed' value must be greater than 0.00")
    @DecimalMax(value = "0.99", message = "The field 'speed' value must be les than 1.00")
    @Column(name = "speed")
    private Double speed;

    @NotNull
    @Min(value = 1, message = "The field 'crewSize' value must be greater than 0")
    @Max(value = 9999, message = "The field 'crewSize' value must be less than 10000")
    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public Ship() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }
}
