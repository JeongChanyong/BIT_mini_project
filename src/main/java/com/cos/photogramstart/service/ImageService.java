package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // 이미지가 저장되는 경로 호출 (application.yml 파일에 경로 등록)
    @Value("${file.path}")
    private String uploadFolder;

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
}
