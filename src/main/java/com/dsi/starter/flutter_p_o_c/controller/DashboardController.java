package com.dsi.starter.flutter_p_o_c.controller;

import com.dsi.starter.flutter_p_o_c.Util.DateUtil;
import com.dsi.starter.flutter_p_o_c.Util.ExpensesUtil;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseIncomeSummaryDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.service.ChargeService;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;
import com.dsi.starter.flutter_p_o_c.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpensesUtil expensesUtil;


    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<ExpenseDTO> expenseDTOList = expenseService.findAll();
        Map<String,Double> expenseIncomeSummaryMap = expenseService.getExpenseIncomeSummaryMap();
        ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO = expenseService.getAnnualExpenseIncomeSummery();
        List<UserDTO> userDTOList = userService.findAll();
        Map<String, Double> userVsChargesMap = new HashMap<>();
        userDTOList.stream().forEach(user -> {
            String key = user.getFirstName();
            Double value = expensesUtil.calculateGrossCharges(chargeService.findAllByUserId(user.getId()));
            userVsChargesMap.put(key, value);
        });

        return loadAdminDashboard(model, expenseDTOList, expenseIncomeSummaryMap, expenseIncomeSummaryDTO, userVsChargesMap);
    }

    @PostMapping("/stats/date")
    public String dashboardStatByDate(Model model, @RequestParam("startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        List<ExpenseDTO> expenseDTOList = expenseService.findAllByDateRange(startDate, endDate);
        Map<String,Double> expenseIncomeSummaryMap = expenseService.getExpenseIncomeSummaryMap(startDate, endDate);
        ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO = expenseService.getAnnualExpenseIncomeSummery(startDate, endDate);
        List<UserDTO> userDTOList = userService.findAll();
        Map<String, Double> userVsChargesMap = new HashMap<>();
        userDTOList.stream().forEach(user -> {
            String key = user.getFirstName();
            Double value = expensesUtil.calculateGrossCharges(chargeService.findAllByUserIdAndDate(user.getId(), startDate, endDate));
            userVsChargesMap.put(key, value);
        });
        return loadAdminDashboard(model, expenseDTOList, expenseIncomeSummaryMap, expenseIncomeSummaryDTO, userVsChargesMap);
    }

    public String loadAdminDashboard(Model model, List<ExpenseDTO> expenseDTOList, Map<String,Double> expenseIncomeSummaryMap, ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO, Map<String, Double> userVsChargesMap){

        String[] months = expenseDTOList.stream().map(x -> x.getDate().getMonth().toString()+"-"+ String.valueOf(x.getDate().getYear())).toArray(String[]::new);
        Double[] incomes = expenseDTOList.stream().map(x -> x.getGrossIncome()).toArray(Double[]::new);
        Double[] expenses = expenseDTOList.stream().map(x -> x.getGrossExpense()).toArray(Double[]::new);
        Double[] revenues = expenseDTOList.stream().map(x -> x.getGrossIncome()-x.getGrossExpense()).toArray(Double[]::new);

        System.out.println(expenseIncomeSummaryMap);
        System.out.println(userVsChargesMap);
        model.addAttribute("userChargesMap", userVsChargesMap);
        model.addAttribute("expenseIncomeSummary",expenseIncomeSummaryDTO);
        model.addAttribute("expenseIncomeSummaryMap",expenseIncomeSummaryMap);
        model.addAttribute("grossExpense", expensesUtil.calculateGrossExpensesFromList(expenseDTOList));
        model.addAttribute("grossIncome", expensesUtil.calculateGrossIncome(expenseDTOList));
        model.addAttribute("grossRevenue", expensesUtil.calculateGrossIncome(expenseDTOList) - expensesUtil.calculateGrossExpensesFromList(expenseDTOList));
        model.addAttribute("incomes",incomes);
        model.addAttribute("months",months);
        model.addAttribute("expenses",expenses);
        model.addAttribute("revenues",revenues);
        return "adminDashboard";
    }
}
