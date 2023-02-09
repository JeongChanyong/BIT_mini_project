package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    // 구독 하기
    @Transactional
    public void subscribe(int fromuserid, int touserid) {
        try {
            subscribeRepository.mSubscribe(fromuserid, touserid);
        }catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    // 구독 취소
    @Transactional
    public void subscribeCancel(int fromuserid, int touserid) {
        try {
            subscribeRepository.mUnsubscribe(fromuserid, touserid);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 취소 하였습니다");
        }

    }
    // 구독자 정보 불어 오기
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {
        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileimageurl, ");
        sb.append("CASE WHEN (SELECT true FROM subscribe WHERE fromuserid = ? AND touserid = u.id) THEN true ELSE false END subscribestate, ");
        sb.append("CASE WHEN (?=u.id) THEN true ELSE false END equalUserState ");
        sb.append("FROM users u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.touserid ");
        sb.append("WHERE s.fromuserid = ?"); // 세미콜론 첨부하면 안됨

        // 1. 물음표 principalId
        // 2. 물음표 principalId
        // 3. 마지막 물음표 pageuserid

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }
}
