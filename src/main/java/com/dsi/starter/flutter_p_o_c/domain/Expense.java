package com.dsi.starter.flutter_p_o_c.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Expense {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double electricity;

    @Column(nullable = false)
    private Double gas;

    @Column(nullable = false)
    private Double water;

    @Column
    private Double security;

    @Column
    private Double cleaner;

    @Column
    private Double lift;

    @Column
    private Double others;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(final Double electricity) {
        this.electricity = electricity;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(final Double gas) {
        this.gas = gas;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(final Double water) {
        this.water = water;
    }

    public Double getSecurity() {
        return security;
    }

    public void setSecurity(final Double security) {
        this.security = security;
    }

    public Double getCleaner() {
        return cleaner;
    }

    public void setCleaner(final Double cleaner) {
        this.cleaner = cleaner;
    }

    public Double getLift() {
        return lift;
    }

    public void setLift(final Double lift) {
        this.lift = lift;
    }

    public Double getOthers() {
        return others;
    }

    public void setOthers(final Double others) {
        this.others = others;
    }

}
