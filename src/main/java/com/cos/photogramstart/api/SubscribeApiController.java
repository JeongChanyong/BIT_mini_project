package com.cos.photogramstart.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 구독 관련 API
public class SubscribeApiController {

    @PostMapping("/api/subscribe/{touserId}")
}
