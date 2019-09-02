package com.kindombiao.redisdemo;

import com.kindombiao.redisdemo.beans.Article;
import com.kindombiao.redisdemo.cache.service.RedisArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisdemoApplicationTests {

    @Autowired
    private RedisArticleService redisArticleService;

    @Test
    public void publishArticle(){

        Article article = new Article();
        article.setTile("一路向西");
        article.setContent("这个电影还不错......");
        article.setLink("www.kingdombiao.com");
        article.setUserId("kingdiaobiao");
        String articleId = redisArticleService.postArticle(article);
        System.out.println("文章发布成功："+articleId);
    }


    @Test
    public void articleVote(){
        redisArticleService.articleVote("kingdombiao","article:10");
        System.out.println("投票成功......");

    }

    @Test
    public void getArticles(){
        List<Map<String, String>> articles = redisArticleService.getArticles(1, "score:article");
        articles.forEach((map)->{
            System.out.println(map);
        });
    }


}
