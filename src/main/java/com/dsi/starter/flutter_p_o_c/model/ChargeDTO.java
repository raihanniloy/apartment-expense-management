package com.dsi.starter.flutter_p_o_c.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class ChargeDTO {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Double serviceCharge;
    @JsonIgnore
    private Long userCharges;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String flatNo;

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

    public Long getUserCharges() {
        return userCharges;
    }

    public void setUserCharges(final Long userCharges) {
        this.userCharges = userCharges;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
