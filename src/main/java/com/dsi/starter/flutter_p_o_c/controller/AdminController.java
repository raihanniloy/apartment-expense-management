package com.dsi.starter.flutter_p_o_c.controller;

import com.dsi.starter.flutter_p_o_c.Util.DateUtil;
import com.dsi.starter.flutter_p_o_c.Util.ExpensesUtil;
import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChargeService chargeService;

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
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}")
    public String updateUsers(@PathVariable("id") Long id, @ModelAttribute("user") UserDTO userDTO){
        String username = userService.get(id).getUsername();
        userService.update(username, userDTO);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUsers(@PathVariable("id") Long id){
        String username = userService.get(id).getUsername();
        userService.delete(username);
        return "redirect:/admin/users";
    }


    @PostMapping("/users/charges/{id}")
    public String createUserCharges(@PathVariable("id") Long id, @ModelAttribute("charge") ChargeDTO chargeDTO){
        chargeDTO.setUserCharges(id);
        Long chargeId = chargeService.create(chargeDTO);
        return "redirect:/admin/users";
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
        return "redirect:/admin/users/charges/"+userId;
    }

    @GetMapping("/users/charges/delete/{id}")
    public String deleteUserCharges(@PathVariable("id") Long id){
        Long userId = chargeService.get(id).getUserCharges();
        chargeService.delete(id);
        return "redirect:/admin/users/charges/"+userId;
    }



}
