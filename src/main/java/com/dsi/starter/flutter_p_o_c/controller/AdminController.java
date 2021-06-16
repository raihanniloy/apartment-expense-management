package com.dsi.starter.flutter_p_o_c.controller;

import com.dsi.starter.flutter_p_o_c.Util.ExpensesUtil;
import com.dsi.starter.flutter_p_o_c.domain.User;
import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.repos.UserRepository;
import com.dsi.starter.flutter_p_o_c.service.ChargeService;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;
import com.dsi.starter.flutter_p_o_c.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpensesUtil expensesUtil;



    @GetMapping("/users")
    public String showAllUsers(Model model){
        List<UserDTO> userDTOList = userService.findAll();
        ChargeDTO chargeDTO = new ChargeDTO();
        model.addAttribute("userInfoList",userDTOList);
        model.addAttribute("charge",chargeDTO);
        model.addAttribute("user", new UserDTO());
        return "users";
    }

    @PostMapping("/users/create")
    public String createUsers(@ModelAttribute("user") UserDTO userDTO){
        Long userId = userService.create(userDTO);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}")
    public String updateUsers(@PathVariable("id") Long id, @ModelAttribute("user") UserDTO userDTO){
        String username = userService.get(id).getUsername();
        userService.update(username, userDTO);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUsers(@PathVariable("id") Long id){
        String username = userService.get(id).getUsername();
        userService.delete(username);
        return "redirect:/users";
    }


    @PostMapping("/users/charges/{id}")
    public String createUserCharges(@PathVariable("id") Long id, @ModelAttribute("charge") ChargeDTO chargeDTO){
        chargeDTO.setUserCharges(id);
        Long chargeId = chargeService.create(chargeDTO);
        return "redirect:/users";
    }

    @GetMapping("/users/charges/{id}")
    public String viewUserCharges(@PathVariable("id") Long id, Model model){
        UserDTO userDTO = userService.get(id);
        List<ChargeDTO> chargeDTOList = chargeService.findAllByUserId(id);
        model.addAttribute("user", userDTO);
        model.addAttribute("chargeList", chargeDTOList);
        return "userCharges";
    }

    @PostMapping("/users/charges/edit/{id}")
    public String editUserCharges(@PathVariable("id") Long id, @ModelAttribute("charge") ChargeDTO chargeDTO){
        chargeService.update(id, chargeDTO);
        Long userId = chargeService.get(id).getUserCharges();
        return "redirect:/users/charges/"+userId;
    }

    @GetMapping("/users/charges/delete/{id}")
    public String deleteUserCharges(@PathVariable("id") Long id){
        Long userId = chargeService.get(id).getUserCharges();
        chargeService.delete(id);
        return "redirect:/users/charges/"+userId;
    }

    @PostMapping("/expenses/edit/{id}")
    public String editExpenses(@PathVariable("id") Long id, @ModelAttribute("expense") ExpenseDTO expenseDTO){
        expenseService.update(id, expenseDTO);
        return "redirect:/allExpenses";
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpenses(@PathVariable("id") Long id){
        expenseService.delete(id);
        return "redirect:/allExpenses";
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
        return "redirect:/allExpenses";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<ExpenseDTO> expenseDTOList = expenseService.findAll();
        model.addAttribute("grossExpense", expensesUtil.calculateGrossExpensesFromList(expenseDTOList));
        model.addAttribute("grossIncome", expensesUtil.calculateGrossIncome(expenseDTOList));
        model.addAttribute("grossRevenue", expensesUtil.calculateGrossIncome(expenseDTOList) - expensesUtil.calculateGrossExpensesFromList(expenseDTOList));
        return "adminDashboard";
    }

    /*
    @GetMapping("/index")
    public String showIndex(){
        return "adminDashboard.html";
    }
    */



}
