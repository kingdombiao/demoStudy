package com.kingdombiao.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-26 16:43
 */
@WebServlet(value = "/asynOrder", asyncSupported = true)
public class AsynOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("主线程开始----" + Thread.currentThread() + "----开始时间：" + System.currentTimeMillis());

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                System.out.println("副线程开始----" + Thread.currentThread() + "----开始时间：" + System.currentTimeMillis());
                //模拟下单
                placeOrder();

                //asyncContext.complete();

                //获取到异步上下文
                AsyncContext reqAsyncContext = req.getAsyncContext();

                //获取响应
                ServletResponse response = reqAsyncContext.getResponse();

                response.getWriter().write("asynOrder.....");

                System.out.println("副线程开始----" + Thread.currentThread() + "----结束时间：" + System.currentTimeMillis());

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("主线程结束----" + Thread.currentThread() + "----结束时间：" + System.currentTimeMillis());
    }


    /**
     * 模拟下单
     */
    private void placeOrder() {
        System.out.println(Thread.currentThread() + "processing...........");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
