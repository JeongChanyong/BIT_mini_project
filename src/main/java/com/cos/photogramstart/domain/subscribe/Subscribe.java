package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
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
        /**
         * 여러개의 unique 제약 조건을 걸기 위한 로직
         * columnNames 값은 실제 DB에 컬럼명을 적어야 함
         * 하나의 컬럼만 unique 일 경우 필드에 적으면 됨
         */
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk", // Unique 제약조건 이름
                        columnNames = { // Unique 제약조건을 적용할 컬럼명
                                "fromuserid",
                                "touserid"
                        }

                )
        }
)
public class Subscribe {

    /**
     * 구독 클래스
     */

    // TODO: 2023-02-04 11:47 번호 증가 전력 AOTO 로 진행하면 구독 하기 api not null 제약조건 위배..왜지? IDENTITY 변경 후동작
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromuserid")
    @ManyToOne
    private User fromUser; // 구독 하는 유저

    @JoinColumn(name = "touserid")
    @ManyToOne
    private User toUser; // 구독 받는 유저

    private LocalDateTime createDate; //

    @PrePersist // db insert 되기 전에 먼저 저장
    @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2020-12-18 23:36:33)
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
