package com.dsi.starter.flutter_p_o_c.controller;

import com.dsi.starter.flutter_p_o_c.Util.DateUtil;
import com.dsi.starter.flutter_p_o_c.Util.ExpensesUtil;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.service.ChargeService;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;
import com.dsi.starter.flutter_p_o_c.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ExpensesController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping("/expenses/edit/{id}")
    public String editExpenses(@PathVariable("id") Long id, @ModelAttribute("expense") ExpenseDTO expenseDTO){
        expenseService.update(id, expenseDTO);
        return "redirect:/admin/allExpenses";
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpenses(@PathVariable("id") Long id){
        expenseService.delete(id);
        return "redirect:/admin/allExpenses";
    }

    @GetMapping("/allExpenses")
    public String showAllExpenses(Model model){
        List<ExpenseDTO> expenseDTOList = expenseService.findAll();
        model.addAttribute("expensesList",expenseDTOList);
        model.addAttribute("expense", new ExpenseDTO());
        return "allExpenses";
    }

    @PostMapping("/expenses")
    public String addNewExpenses(@ModelAttribute("expenses") ExpenseDTO expenseDTO){
        Long id = expenseService.create(expenseDTO);
        return "redirect:/admin/allExpenses";
    }

}
