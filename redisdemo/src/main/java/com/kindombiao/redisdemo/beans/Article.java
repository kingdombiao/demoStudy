package com.kindombiao.redisdemo.beans;

import lombok.Data;

/**
 * 文章
 */
@Data
public class Article {

    private String tile;
    private String content;
    private String link;
    private String userId;
}
