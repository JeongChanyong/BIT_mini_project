package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    // 구독하기
    @Modifying
    @Query(value = "INSERT INTO subscribe(fromuserid, touserid, createDate) VALUES(:fromuserid, :touserid, now())", nativeQuery = true)
    void mSubscribe(int fromuserid, int touserid);

    // 구독취소하기
    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromuserid = :fromuserid AND touserid = :touserid", nativeQuery = true)
    void mUnsubscribe(int fromuserid, int touserid);
    // 구독상태 확인

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromuserid=:principalId AND touserid=:pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    // 구독자수 확인
    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromuserid=:pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);

}
