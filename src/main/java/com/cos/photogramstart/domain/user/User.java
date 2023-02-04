package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor //  빈 생성자
@AllArgsConstructor // 전체 생성자
@Data // 자동으로 Getter, Setter, toString  만듬
@Entity // DB 테이블과 매핑, JPA 에서 관리
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략, postgresSQL 전략은 SEQUENCE 사용
    private int id; // pk 지정

    @Column(unique = true, length = 20) // 해당 컬럼은 unique 속성을 통해 중복을 방지하고 varchar(20) 지정
    private String username;

    private String password;

    private String name;

    private String website;

    private String bio; // 자기소개

    private String email;

    private String phone;

    private String gender;

    private String profileImageUrl;; // 프로필 사진

    private String role; // 권한

    private LocalDateTime createDate; //

    @PrePersist // db insert 되기 전에 먼저 저장
    @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2020-12-18 23:36:33)
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
