package com.cos.photogramstart.dto.users;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    /**
     * user profile 업데이트용 dto
     */
    @NotBlank
    private String name;
    @NotBlank

    private String password;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 필수 항목들이 아닌 값들을 처리 하기엔 위험한 코드
    public User toEntity(){
        return User.builder()
                .name(name) // 이름도 validation check 해야함
                .password(password) // 사용자가 패스워드를 기재 안했으면 문제 발생 validation check 필수
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
