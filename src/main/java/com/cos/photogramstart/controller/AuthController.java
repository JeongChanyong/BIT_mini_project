package com.cos.photogramstart.controller;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller // 컨트롤러 어노테이션을 명시해줘야 spring에서 해당 클래스가 컨트롤러임을 알수 있고 view 파일을 리턴함
@RequiredArgsConstructor // final 이 되어 있는 객체들의 생성자를 생성 해줌
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String>errMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패 함", errMap); // 해당 오류가 발생하면 handler
        } else {
            User user = signupDto.toEntity();
            User userEntity = authService.userSave(user);
        }
        return "auth/signin";
    }


}
