package com.cos.photogramstart.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignupDto {
//    @Max(value = 20, message = "최대 글자수는 20 글자 입니다.") // max 메서드 지원항목은 숫자인데 username의 경우 String 이여서 계속 예외 발생
    @Size(min = 2, max = 20)
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    @NotBlank
    @Email(message = "이메일 형식으로 입력해 주세요")
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
