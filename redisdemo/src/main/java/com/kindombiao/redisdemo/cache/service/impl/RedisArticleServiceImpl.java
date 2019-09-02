package com.kindombiao.redisdemo.cache.service.impl;

import com.kindombiao.redisdemo.beans.Article;
import com.kindombiao.redisdemo.cache.service.RedisArticleService;
import com.kindombiao.redisdemo.utils.JedisUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文章发布
 */
@Service
@Slf4j
public class RedisArticleServiceImpl implements RedisArticleService {

    @Autowired
    private JedisUtils jedis;


    /**
     * 发布文章
     *
     * @param article
     * @return
     */
    @Override
    public String postArticle(Article article) {

        //文章id 使用自增，类似于uuid
        String articleId = String.valueOf(jedis.incr("article"));

        //记录投票（默认给自己投一票）
        String votedKey = "voted:" + articleId;
        jedis.sadd(votedKey, article.getUserId());

        //生成文章key
        String articleKey = "article:" + articleId;
        long nowTime = System.currentTimeMillis() / 1000;

        //保存文章
        Map<String, String> articleData = new HashMap<>();
        articleData.put("tile", article.getTile());
        articleData.put("content", article.getContent());
        articleData.put("link", article.getLink());
        articleData.put("publishTime", String.valueOf(nowTime));
        articleData.put("votes", "1"); //投票数，默认投一票
        jedis.hmset(articleKey, articleData);

        //记录文章的评分集合，用于排序
        jedis.zadd("score:article", (nowTime + 200), articleKey);

        //记录文章的发布时间集合，用于排序
        jedis.zadd("publishtime:article", nowTime, articleKey);

        //设置文章的过期时间
        DateTime dateTime = new DateTime(new Date()).plusDays(7);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(dateTime.toDate());
        log.info("文章的过期时间为；"+format);
        jedis.expire(articleKey, Long.valueOf(dateTime.toDate().getTime() / 1000).intValue());

        log.info("当前时间戳为："+new Date().getTime()/1000);
        log.info("文章的过期时间戳为；"+dateTime.toDate().getTime()/1000);
        return articleId;
    }


    /**
     * 文章投票
     *
     * @param userId 用户id
     * @param articleKey 文章key （例如：article:001）
     */
    @Override
    public void articleVote(String userId, String articleKey) {

        //获取该文章的发布时间
        //String publishTime = jedis.hget(articleKey, "publishtime");

        /*if(Long.valueOf(publishTime)<(System.currentTimeMillis() / 1000)){
            log.info("该{}文章已过期....",articleKey);
            return;
        }*/

        //Double zscore = jedis.zscore("publishtime:article", articleKey);


        if(jedis.getKeyTtl(articleKey)<(System.currentTimeMillis() / 1000)){
            log.info("该{}文章投票时间已经截止.....",articleKey);
            return;
        }


        //文章id
        String articleId=articleKey.substring(articleKey.indexOf(":")+1);

        //将投票的用户加入到key=voted:articleId的集合中，成功返回：1 失败返回：0
        if(jedis.sadd("voted:"+articleId,userId)==1){

            //评分加200
            jedis.zincrby("score:article",200,articleKey);

            //增加投票数
            jedis.hincrby(articleKey,"votes",1L);
        }
    }

    /**
     * 查询文章列表（分页）
     * @param page
     * @param scoreArticleKey
     * @return
     */
    @Override
    public List<Map<String, String>> getArticles(int page, String scoreArticleKey) {
        int pageSize=10;
        int start=(page-1)*pageSize;
        int end=start+(pageSize-1);

        Set<String> articleKeys = jedis.zrevrange(scoreArticleKey, start, end);

        List<Map<String,String>> articleList = new ArrayList<Map<String,String>>();

        for (String articleKey : articleKeys) {
            Map<String, String> articleData = jedis.hgetAll(articleKey);
            articleData.put("articleId",articleKey);
            articleList.add(articleData);
        }
        return articleList;
    }

    @Override
    public String hget(String key, String field) {
        return jedis.hget(key,field);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return jedis.hgetAll(key);
    }
}
