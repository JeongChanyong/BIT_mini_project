package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UsersRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // user 정보
    public User userProfile(int userid) {
        // select * form image where userid = userid
        // userid 없을 경우 Optional 처리를 해야 한다...
        User userEntity = usersRepository.findById(userid).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });
        return userEntity;

    }


    @Transactional
    public User userUpdate(int id, User user) {
        // 1. 영속화 - 회원 찾기
        User userEntity = usersRepository.findById(id).orElseThrow(()-> { // DB에 없는 id를 찾을 경우
            return new CustomValidationApiException("찾을수 없는 id 입니다.");});

        // 2. 영속화된 오브젝트 수정 - 더티체킹(업데이트 완료)
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }
}
