package com.cos.photogramstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {

    @GetMapping({"/", "image/story"}) // 두개의 GetMapping 은 {} 사용하면 가능 함
    public String story(){
        return "image/story";
    }

    @GetMapping("image/popular") // 인기글로 이동
    public String popular(){
        return "image/popular";
    }

    @GetMapping("image/upload")
    public String upload(){
        return "image/upload";
    }
}
