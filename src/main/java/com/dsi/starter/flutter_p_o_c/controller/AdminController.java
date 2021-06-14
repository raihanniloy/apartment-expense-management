package com.dsi.starter.flutter_p_o_c.controller;

import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.repos.UserRepository;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;
import com.dsi.starter.flutter_p_o_c.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    /*@GetMapping("/dashboard")
    public String showDashboard(Model model){
        List<UserDTO> userDTOList = userService.findAll();
        ExpenseDTO expenseDTO = new ExpenseDTO();
        model.addAttribute("userInfoList",userDTOList);
        model.addAttribute("expense",expenseDTO);
        return "adminDashboard";
    }

    @PostMapping("/users/expenses/{id}")
    public String createUserExpense(@PathVariable Long id, @ModelAttribute("expense") ExpenseDTO expenseDTO){
        expenseDTO.setDate(LocalDate.now());
        expenseDTO.setUserExpense(id);
        Long userId = expenseService.create(expenseDTO);
        return "redirect:/allExpenses";
    }

    @GetMapping("/allExpenses")
    public String showAllExpenses(Model model){
        List<ExpenseDTO> expenseDTOList = expenseService.findAll();
        model.addAttribute("expensesList",expenseDTOList);
        return "allExpenses";
    }

    @GetMapping("/index")
    public String showIndex(){
        return "adminDashboard.html";
    }

    @GetMapping("/login")
    public String showLogin(){

        return "login";
    }*/

}
