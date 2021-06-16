package com.dsi.starter.flutter_p_o_c.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/admin")
    public String showLogin(){
        return "login";
    }



}
