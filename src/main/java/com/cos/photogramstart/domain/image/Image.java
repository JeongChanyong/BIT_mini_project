package com.cos.photogramstart.domain.image;

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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; //사진 설명
    private String postImageUrl; // 사진 전송을 받아서 사진을 특정 폴더에 저장-> DB에 저장된 경로를 insert

    @JoinColumn(name="userid")
    @ManyToOne
    private User user;

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    /**
     * 오브젝트를 콘솔에 출력할 떄 문제가 될 수 있음 필드 부분중 User 부분을 출력 안되게 함
     */
/*    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", createDate=" + createDate +
                '}';
    }*/
}
