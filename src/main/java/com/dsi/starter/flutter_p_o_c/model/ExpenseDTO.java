package com.dsi.starter.flutter_p_o_c.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;


public class ExpenseDTO implements Comparable<ExpenseDTO>{

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Double electricity;

    @NotNull
    private Double gas;

    @NotNull
    private Double water;

    private Double security = 0.0;

    private Double cleaner = 0.0;

    private Double lift = 0.0;

    private Double others = 0.0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double grossIncome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double grossExpense;

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

    public Double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(Double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public Double getGrossExpense() {
        return grossExpense;
    }

    public void setGrossExpense(Double grossExpense) {
        this.grossExpense = grossExpense;
    }

    @Override
    public int compareTo(ExpenseDTO expenseDTO) {
        return this.date.compareTo(expenseDTO.getDate());
    }
}
