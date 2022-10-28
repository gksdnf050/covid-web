package com.covid.web.controller;

import com.covid.web.model.entity.User;
import com.covid.web.service.UserService;
import com.covid.web.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ViewController {

    private UserService userService;

    @GetMapping("/main")
    public String mainView(@RequestParam(name = "mode", defaultValue = "all") String mode, ModelMap modelMap) {
        modelMap.addAttribute("mode", mode);
        return "main";
    }

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUpView() {
        return "signUp";
    }

    // 회원가입 처리
    @PostMapping("sign-up")
    public String signUp(User userDto, RedirectAttributes redirectAttributes) {
        String email = userDto.getEmail();
        UserEntity user = userService.getUser(email);   // 이메일로 중복 확인

        if(user == null){   // 중복된 이메일이 아니라면
            userService.signUp(userDto);    // 회원가입
            return "redirect:/login";   // 회원가입 후 로그인 페이지로 리다이렉트
        }else{  // 중복된 이메일인 경우
            redirectAttributes.addFlashAttribute("signUpFailMsg", "이 이메일 주소는 이미 사용 중입니다."); // 메시지를 flashMap에 담고 리다이렉트
            return "redirect:/sign-up";
        }
    }
}