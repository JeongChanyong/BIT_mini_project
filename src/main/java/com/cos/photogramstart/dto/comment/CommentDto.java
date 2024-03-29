package com.cos.photogramstart.dto.comment;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

// NotNull = Null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null 체크 공백까지

@Data
public class CommentDto {
    @NotBlank // 빈값이거나 null 체크 공백까지
    private String content;
    @NotNull // 빈값이거나 null 체크
    private Integer imageId;

    // toEntity 불필요
}
