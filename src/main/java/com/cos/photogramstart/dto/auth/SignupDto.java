package com.cos.photogramstart.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
//    @Max(value = 20, message = "최대 글자수는 20 글자 입니다.") // max 메서드 지원항목은 숫자인데 username의 경우 String 이여서 계속 예외 발생
    @Size(min = 2, max = 20)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    // 빌더 패턴을 이용해서 지금 필요한 내용만 저장
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }

}
