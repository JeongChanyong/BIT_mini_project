package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk", // Unique 제약조건 이름
                        columnNames = { // Unique 제약조건을 적용할 컬럼명 중복 Unique 설정
                                "imageId",
                                "userId"
                        }

                )
        }
)
public class Likes { // N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; // 1

    @JsonIgnoreProperties({"images"}) // User 클래스의 Image 객체의 참조변수
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // 1


    /**
     * 네이티브 쿼리 사용으로 인해 날짜 데이터 입력 불가 -> Repository 에서 직접 입력 해야 함
     */
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
