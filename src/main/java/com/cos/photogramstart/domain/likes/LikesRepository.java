package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Modifying
    @Query(value = "INSERT INTO likes(imageId,userId,createDate) VALUES (:imageId, :principalId, now())",nativeQuery = true)
    int MLikes(int imageId, int principalId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId=:imageId AND userID=:principalId",nativeQuery = true)
    int MLikesCancel(int imageId, int principalId);

}
