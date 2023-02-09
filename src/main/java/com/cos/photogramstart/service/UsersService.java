package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UsersRepository;
import com.cos.photogramstart.dto.users.UserProfileDto;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // user 정보
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserProfileDto userProfile(int pageUserId, int principalId) {

        UserProfileDto userProfileDto = new UserProfileDto();

        // select * form image where userid = userid
        // userid 없을 경우 Optional 처리를 해야 한다...
        User userEntity = usersRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId);
        userProfileDto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        userProfileDto.setSubscribeState(subscribeState == 1);
        userProfileDto.setSubscribeCount(subscribeCount);

        // 유저 페이지에 사진 좋아요 표시
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });

        return userProfileDto;
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

    @Value("${file.path}")
    private String uploadFolder;
    @Transactional
    public User userProfilePhoto(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+ "_" + profileImageFile.getOriginalFilename(); // 1. jpg
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        try {
            Files.write(imageFilePath,profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        User userEntity = usersRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);
        return userEntity;
    }
}
