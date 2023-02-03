package com.cos.photogramstart.controller;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Slf4j
@Controller
public class UsersController {

    @GetMapping("user/{id}")
    public String profile(@PathVariable Integer id){
        return "user/profile";
    }

    @GetMapping("user/{id}/update") // user/id(pk)/update
    public String update(@PathVariable Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return "user/update";
    }
    /**
     * @AuthenticationPrincipal
     * 세션 정보를 가져오기 위한 스프링에서 지원하는 어노테이션
     */



}
