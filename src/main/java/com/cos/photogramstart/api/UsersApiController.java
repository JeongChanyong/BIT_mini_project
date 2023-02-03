package com.cos.photogramstart.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.dto.CMRespDto;
import com.cos.photogramstart.dto.users.UserUpdateDto;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController // file 반환이 아닌 data 를 보내줘야 하기에 RestController 방식
public class UsersApiController {

    private final UsersService usersService;

    /**
     *
     * json type 명시 !! postman에서는 정상 동작 함...
     * @param userUpdateDto
     * @return
     */
    @PutMapping(value = "api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult, // @Valid 가 적혀 있는 다음 파라미터에 적어야 적용 됨!!!!
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패 함", errMap); // 해당 오류가 발생하면 handler
        } else {
            User userEntity = usersService.userUpdate(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); // 세션 정보 변경
            return new CMRespDto<>(1,"회원수정 완료", userEntity);
        }
    }




//    @PutMapping(value = "api/user/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public CMRespDto<?> update(@RequestBody @PathVariable int id, UserUpdateDto userUpdateDto,
//                        @AuthenticationPrincipal PrincipalDetails principalDetails){
//
//        User userEntity = usersService.회원수정(id, userUpdateDto.toEntity());
//        principalDetails.setUser(userEntity);
//        return new CMRespDto<>(1,"회원수정 완료", userEntity);
//    }
}
