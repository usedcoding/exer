package com.example.exer.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleForm {
    @NotEmpty(message = "제목은 필수 사항 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 사항 입니다.")
    private String content;

}
