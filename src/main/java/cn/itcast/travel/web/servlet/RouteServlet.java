package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pagesizeStr = req.getParameter("pagesize");
        String cidStr = req.getParameter("cid");

        int currentPage = 0;//当前页码 如果未指定默认 = 1
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {

            currentPage = 1;
        }
        int pagesize = 0;//每页显示条数
        if (pagesizeStr != null && pagesizeStr.length() > 0) {
            pagesize = Integer.parseInt(pagesizeStr);

        } else {
            pagesize = 5;

        }
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        //调用service 查询
        RouteService routeService = new RouteServiceImpl();
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pagesize);
        //将查询结果序列化未json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(routePageBean);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);

    }


}
