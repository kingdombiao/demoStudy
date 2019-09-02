package com.kindombiao.redisdemo.cache.service;

import com.kindombiao.redisdemo.beans.Article;

import java.util.List;
import java.util.Map;

public interface RedisArticleService {
    String postArticle(Article article);

    Map<String, String> hgetAll(String key);

    void articleVote(String userId, String articleKey);

    String hget(String key, String votes);

    List<Map<String, String>> getArticles(int page, String order);
}
