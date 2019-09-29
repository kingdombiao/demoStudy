package com.kingdombiao.initializer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 描述:
 * SpringMVC只扫描Controller子容器
 *
 * @author biao
 * @create 2019-09-26 14:30
 */
@ComponentScan(value = "com.kingdombiao",
        includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})},
        useDefaultFilters = false)
@EnableWebMvc
public class BiaoAppConfig {
}
