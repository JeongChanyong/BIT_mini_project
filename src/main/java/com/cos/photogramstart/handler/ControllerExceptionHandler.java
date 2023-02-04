package com.cos.photogramstart.handler;

import com.cos.photogramstart.dto.CMRespDto;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;




@Slf4j
@RestController // data 를 보내줌
@ControllerAdvice // 예외 발생시 모든 예외를 낚아 챈다
public class ControllerExceptionHandler {
    // 스크립트
    @ExceptionHandler(CustomValidationException.class) //  예외가 발생 하면 예외를 해당 함수에서 처리 하고 메시지를 화면에 보여줌
    public String validationException(CustomValidationException e){

        /** CmRespDto, Script 비교
         * 클라이언트에게 응답 할땐 Script
         * Ajax 통신 - CmRespDto 로 받는다.
         * Android 통신 - CmRespDto
         */

        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());// 사용자에게 에러창을 js로 보냄
        }


    }
    // Ajax
    @ExceptionHandler(CustomValidationApiException.class) //  예외가 발생 하면 예외를 해당 함수에서 처리 하고 메시지를 화면에 보여줌
    public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e){
        log.info("실행 여부 확인");
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class) // 구독 오류
    public ResponseEntity<CMRespDto<?>>  apiException(CustomApiException e){
        log.info("실행 여부 확인");
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
