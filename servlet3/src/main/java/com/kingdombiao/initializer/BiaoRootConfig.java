package com.kingdombiao.initializer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 描述:
 * Spring 容器不扫描controller，父容器
 *
 * @author biao
 * @create 2019-09-26 14:25
 */
@ComponentScan(value = "com.kingdombiao",
        excludeFilters ={@Filter(type = FilterType.ANNOTATION,classes = {Controller.class})})
public class BiaoRootConfig {
}
