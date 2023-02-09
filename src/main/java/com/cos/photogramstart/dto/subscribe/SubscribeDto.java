package com.cos.photogramstart.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeDto {

    // 로그인한 유저가 모달에서 확인할 유저의 ID
    private int id;

    // 로그인한 유저가 모달에서 확인할 유저의 이름
    private String username;

    // 로그인한 유저가 모달에서 확인할 유저의 프로필 이미지
    private String profileImageUrl;

    // 로그인한 유저가 모달에서 확인한 유저를 구독했는지에 대한 여부
    private boolean subscribeState;

    // 로그인한 유저가 모달에서 확인한 유저가 본인 인지에 대한 여부
    private boolean equalUserState;

}
