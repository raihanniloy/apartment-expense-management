package com.dsi.starter.flutter_p_o_c.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MonthlyChargesDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private List<ChargeDTO> chargeDTOList;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ChargeDTO> getChargeDTOList() {
        return chargeDTOList;
    }

    public void setChargeDTOList(List<ChargeDTO> chargeDTOList) {
        this.chargeDTOList = chargeDTOList;
    }
}
