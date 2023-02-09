package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UsersRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Comment commentWrite(String content,int imageId, int userId) {

        // Tip (객체를 만들 때 id 값만 담아서 insert 할 수 있다.
        // 대신 return 시에 image 객체와 user 객체는 id 값 만 가지고 있는 빈 객체를 리턴받는다.
        //User user = new User();
        //user.setId(userId);

        Image image = new Image();
        image.setId(imageId);

        User userEntity = usersRepository.findById(userId).orElseThrow(()->{
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });
        userEntity.setId(userId);


        // 댓글
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return commentRepository.save(comment);
    }

    @Transactional
    public void commentDelete(int id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
