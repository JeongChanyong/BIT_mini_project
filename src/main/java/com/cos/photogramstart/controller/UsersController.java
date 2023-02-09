package com.cos.photogramstart.controller;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.dto.users.UserProfileDto;
import com.cos.photogramstart.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Slf4j
@RequiredArgsConstructor
@Controller
public class UsersController {

    private final UsersService usersService;

    @GetMapping("user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        UserProfileDto userProfileDto = usersService.userProfile(pageUserId, principalDetails.getUser().getId()); // url id 가 있으면 해당 로직 수행하고 반환
        model.addAttribute("dto", userProfileDto);
        return "user/profile";
    }

    @GetMapping("user/{id}/update") // user/id(pk)/update
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return "user/update";


    }
    /**
     * @AuthenticationPrincipal
     * 세션 정보를 가져오기 위한 스프링에서 지원하는 어노테이션
     */



}
