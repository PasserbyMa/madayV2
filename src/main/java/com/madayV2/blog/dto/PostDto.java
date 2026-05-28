package com.madayV2.blog.dto;

import java.util.Date;
import lombok.Data;

//Lombok 라이브러리가 제공하는 어노테이션
@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String author;
    private Date createdAt;
    private Date updatedAt;
}