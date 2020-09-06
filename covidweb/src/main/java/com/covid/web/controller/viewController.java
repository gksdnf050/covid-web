package com.covid.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class viewController {
    @GetMapping("/main")
    public String mainView(@RequestParam(name = "mode", defaultValue = "All") String mode, ModelMap modelMap) {
        modelMap.addAttribute("mode", mode);
        return "main";
    }

    @GetMapping("/login")
    public String loginView(){
    	//System.out.println(request.getAttribute("loginFailMsg"));
        return "login";
    }
}
