package com.dsi.starter.flutter_p_o_c.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Charge {

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

    @Column
    private LocalDate date;

    @Column
    private Double serviceCharge;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_charges_id")
    private User userCharges;

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

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(final Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public User getUserCharges() {
        return userCharges;
    }

    public void setUserCharges(final User userCharges) {
        this.userCharges = userCharges;
    }

}
