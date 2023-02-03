package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cos.photogramstart.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // Write(Insert, Update, Delete)
    public User userSave(User user) {
        String rawPassword = user.getPassword(); // 회원 가입시 입력된 패스워드를 가져와서 변수에 저장
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드를 해쉬로 변환
        user.setPassword(encPassword); // 유저의 password >> 암호화된 password
        user.setRole("ROLE_USER"); // 회원 가입시 유저의 권한을 지정 / 관리자 계정을 ROLE_ADMIN 으로 지정
        User usersEntity = usersRepository.save(user); // db에 저장된 user 의 값을 Users type 의 usersEntity 에 저장
        return usersEntity;

    }


}
