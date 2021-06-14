package com.dsi.starter.flutter_p_o_c.Util;

import com.dsi.starter.flutter_p_o_c.domain.Charge;
import com.dsi.starter.flutter_p_o_c.domain.Expense;
import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpensesUtil {

    public double calculateGrossExpenses(Expense expense){
        double gross = expense.getCleaner()+expense.getElectricity()+ expense.getGas()+ expense.getLift()+ expense.getOthers()+expense.getWater()+ expense.getSecurity();
        return gross;
    }

    public double calculateGrossCharges(List<ChargeDTO> chargeDTOS){
        double gross = chargeDTOS.stream()
                .map(x -> x.getServiceCharge())
                .reduce((double) 0, Double::sum);
        return gross;
    }
}