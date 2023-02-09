package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likes(int imageId, int principalId) {
        likesRepository.MLikes(imageId, principalId);
    }
    @Transactional
    public void likesCancel(int imageId, int principalId) {
        likesRepository.MLikesCancel(imageId, principalId);
    }

}
