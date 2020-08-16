package com.covid.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class viewController {
    @GetMapping("/main")
    public String testController(@RequestParam(name = "mode", defaultValue = "All") String mode, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("mode", mode);
        return "main";
    }
}
