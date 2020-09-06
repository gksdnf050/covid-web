package com.covid.web.controller;

import com.covid.web.dto.Member;
import com.covid.web.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class viewController {

    private MemberService memberService;

    @GetMapping("/main")
    public String mainView(@RequestParam(name = "mode", defaultValue = "all") String mode, ModelMap modelMap) {
        modelMap.addAttribute("mode", mode);
        return "main";
    }

    @GetMapping("/login")
    public String loginView(@RequestParam(name = "error", required = false) String error, Model model){
        model.addAttribute("error",error);
        return "login";
    }

    @GetMapping("/signup")
    public String getSignup() {
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("signup")
    public String postSignup(Member member) {
        memberService.joinUser(member);

        return "redirect:/login";
    }
}