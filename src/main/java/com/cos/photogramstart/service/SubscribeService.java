package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Slf4j
@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(int fromuserid, int touserid) {
        try {
            subscribeRepository.mSubscribe(fromuserid, touserid);

        }catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }


    }
    @Transactional
    public void subscribeCancel(int fromuserid, int touserid) {
        try {
            subscribeRepository.mUnsubscribe(fromuserid, touserid);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 취소 하였습니다");
        }

    }
}
