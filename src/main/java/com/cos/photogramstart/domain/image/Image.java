package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image { // n:1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; //사진 설명
    private String postImageUrl; // 사진 전송을 받아서 사진을 특정 폴더에 저장-> DB에 저장된 경로를 insert

    @JsonIgnoreProperties({"images"}) // images 무시
    @JoinColumn(name="userid")
    @ManyToOne(fetch = FetchType.EAGER) // 이미지를 select 하면 조인해서 User 정보를 같이 들고옴
    private User user;


    // 이미지 좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image") // fk 만들지 않기
    private List<Likes> likes;

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @Transient // DB에 컬럼이 만들어 지지 않는다.
    private boolean likeState;

    @Transient // DB 컬럼 생성 방지
    private int likeCount;

    // 댓글
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;


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
