package com.cos.photogramstart.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.dto.CMRespDto;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;


    // Pageable -> import org.springframework.data.domain.Pageable; 임포트 주의
    @GetMapping("api/image") // 모든 이미지를 가져옴
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size=3)Pageable pageable) {
        Page<Image> images = imageService.imageStory(principalDetails.getUser().getId(),pageable);
        return new ResponseEntity<>(new CMRespDto<>(1,"성공", images), HttpStatus.OK);
    }

    /**
     * 좋아요 컨트롤러
     */
    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED); // 201번 코드 DB에 insert 할경우
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping ("/api/image/{imageId}/likes")
    public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.likesCancel(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소", null), HttpStatus.OK);
    }

}
