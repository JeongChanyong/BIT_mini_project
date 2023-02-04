package com.cos.photogramstart.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.dto.CMRespDto;
import com.cos.photogramstart.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController // 구독 관련 API
public class SubscribeApiController {
    // TODO: 2023-02-04 12:37 // 의존성 주입때 final 생략... api 테스트시 500에러 발생...잘 하자..

    private final SubscribeService subscribeService;

    // 구독하기
    @PostMapping("/api/subscribe/{touserid}")
    public ResponseEntity<?> subscribe(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int touserid) {
        log.info(""+touserid);
        log.info(""+principalDetails.getUser().getId());
        subscribeService.subscribe(principalDetails.getUser().getId(), touserid);
        log.info("" + touserid);
        return new ResponseEntity<>(
                new CMRespDto<>(1, "구독하기성공", null), HttpStatus.OK);
    }

    // 구독취소하기
    @DeleteMapping("/api/subscribe/{touserid}")
    public ResponseEntity<?> unSubscribe(
            @AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int touserid) {

        subscribeService.subscribeCancel(principalDetails.getUser().getId(), touserid);
        return new ResponseEntity<>(
                new CMRespDto<>(1, "구독취소하기성공", null), HttpStatus.OK);
    }

}
