package com.kingdombiao.Controller;

import com.kingdombiao.service.BiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 13:47
 */
@Controller
public class BiaoController {

    @Autowired
    private BiaoService biaoService;

    @RequestMapping("/buy")
    @ResponseBody
    public String buy(){
        return biaoService.getBuy("123456");
    }
}
