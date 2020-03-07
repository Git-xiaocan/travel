package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法分发  获取访问路径
        String uri = req.getRequestURI();
        //获取方法名称
        String methonName = uri.substring(uri.lastIndexOf("/") + 1);

        //反射获取方法

        try {
            Method  method = this.getClass().getMethod(methonName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this, req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
