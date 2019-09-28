package com.kingdombiao.controller;


import com.kingdombiao.annotation.KingdomBiaoController;
import com.kingdombiao.annotation.KingdomBiaoQualifier;
import com.kingdombiao.annotation.KingdomBiaoRequestMapping;
import com.kingdombiao.annotation.KingdomBiaoRequestParam;
import com.kingdombiao.service.BiaoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@KingdomBiaoController
@KingdomBiaoRequestMapping("/biao")
public class BiaoController {

    @KingdomBiaoQualifier("biaoServiceImpl")
    private BiaoService biaoService;


    @KingdomBiaoRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
                      @KingdomBiaoRequestParam("name") String name,
                      @KingdomBiaoRequestParam("age") String age) {

        try {
            String result = biaoService.query(name, age);
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @KingdomBiaoRequestMapping("/insert")
    public void insert(HttpServletResponse response) {
        try {
            PrintWriter pw = response.getWriter();
            String result = biaoService.insert("0000");

            pw.write(result);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @KingdomBiaoRequestMapping("/update")
    public void update(HttpServletResponse response, String param) {
        try {
            PrintWriter pw = response.getWriter();
            String result = biaoService.update(param);

            pw.write(result);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
