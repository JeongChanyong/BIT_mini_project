package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "select * from image where userid in (select touserid from subscribe where fromuserid = :principalId) ORDER BY createdate desc",nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);
    // Pageable 매개변수로 받아서 반환 타입이 List<Image> -> Page<Image> 변경
    // ORDER BY DESC 는 우리 DB 에서 안됨...ORDER BY createdate desc; 로 해결


    @Query(value = "select i.* from image i inner join (select imageId, COUNT(imageId) likeCount from likes group by imageId) c on i.id = c.imageId order by likeCount desc",nativeQuery = true)
    List<Image> mPopular();
}
