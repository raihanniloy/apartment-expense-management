package com.dsi.starter.flutter_p_o_c.model;

import javax.validation.constraints.NotNull;

public class ExpenseIncomeSummaryDTO {

    private Double electricity = (double)0;

    private Double gas = (double)0;

    private Double water = (double)0;

    private Double security = (double)0;

    private Double cleaner = (double)0;

    private Double lift = (double)0;

    private Double others = (double)0;

    private Double totalIncome = (double)0;

    private Double totalExpense = (double)0;

    private Double totalRevenue = (double)0;

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public Double getSecurity() {
        return security;
    }

    public void setSecurity(Double security) {
        this.security = security;
    }

    public Double getCleaner() {
        return cleaner;
    }

    public void setCleaner(Double cleaner) {
        this.cleaner = cleaner;
    }

    public Double getLift() {
        return lift;
    }

    public void setLift(Double lift) {
        this.lift = lift;
    }

    public Double getOthers() {
        return others;
    }

    public void setOthers(Double others) {
        this.others = others;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return "ExpenseIncomeSummaryDTO{" +
                "electricity=" + electricity +
                ", gas=" + gas +
                ", water=" + water +
                ", security=" + security +
                ", cleaner=" + cleaner +
                ", lift=" + lift +
                ", others=" + others +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
