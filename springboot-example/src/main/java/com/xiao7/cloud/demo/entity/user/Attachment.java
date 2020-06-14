package com.xiao7.cloud.demo.entity.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 附件
 */
@Data
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String contentType;
    private String author;
    private String language;
    private String title;
    private String content;
}
