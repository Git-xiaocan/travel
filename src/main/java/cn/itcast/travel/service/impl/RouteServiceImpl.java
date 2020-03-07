package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pagesize) {
        //封装pagebean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pagesize);
        //设置总记录数
        int totalcount = routeDao.findtotalCount(cid);
        pb.setTotalCount(totalcount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pagesize; //开始的记录数
        List<Route> list = routeDao.findByPage(cid, start, pagesize);
        pb.setList(list);
        //设置总页数
        int totalpage = totalcount % pagesize == 0 ? totalcount / pagesize : (totalcount / pagesize) + 1;
        pb.setTotalPage(totalpage);

        return pb;
    }
}
