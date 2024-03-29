package com.cos.photogramstart.controller;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.dto.image.ImageUploadDto;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "image/story"}) // 두개의 GetMapping 은 {} 사용하면 가능 함
    public String story(){
        return "image/story";
    }

    // 인기페이지
    @GetMapping("image/popular")
    public String popular(Model model){
        List<Image> images = imageService.bestPhoto();

        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping("image/upload")
    public String upload(){

        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails ){

        if (imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부 되지 않았습니다.", null);
        }
        imageService.imageUpload(imageUploadDto, principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId();
    }



}
