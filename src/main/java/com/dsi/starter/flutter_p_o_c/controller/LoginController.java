package com.dsi.starter.flutter_p_o_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/")
    public String home(){
        return "redirect:/admin/dashboard";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "Access Denied!!");
        return "redirect:/login";
    }

}
