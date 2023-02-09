package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // 이미지가 저장되는 경로 호출 (application.yml 파일에 경로 등록)
    @Value("${file.path}")
    private String uploadFolder;



    @Transactional
    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

        /**
         * UUID : 범용 고유 식별자
         * 고유성이 보장 되는 ID를 만들어 준다. 128비트의 숫자로서 총 32자리 16진수의 숫자를 8-4-4-4-12 자리로 하이픈이 추가 되어 구분
         *
         */
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+ "_" + imageUploadDto.getFile().getOriginalFilename(); // 실제 파일 이름을 가져와서 imageFileName 저장
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        try {
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

         /**
         * 이미지 테이블 저장
         * Image 객체와 imageUploadDto type 달라서 저장할 수가 없다.
         */
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true) // insert 안하고 읽기만 함
    // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) readOnly로 해서 해당 작업을 안함
    public Page<Image> imageStory(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // images 에 좋아요 상태 담기
        images.forEach(image -> {

            image.setLikeCount(image.getLikes().size()); // 이미지의 좋아요 count 수

            log.info("테스트"+image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) { // 해당 이미지의 좋아요한 사람들을 찾아서 현재 로그인 유저와 비교. 좋아요 했으면
                    image.setLikeState(true);
                }
            });
        });
        return images;
    }


    public List<Image> bestPhoto() {
        return imageRepository.mPopular();
    }
}
