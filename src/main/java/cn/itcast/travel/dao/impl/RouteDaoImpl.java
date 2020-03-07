package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findtotalCount(int cid) {

        String sql = "select count(*) from tab_route where cid = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class,cid);
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pagesize) {
        String sql = "select * from tab_route where cid = ? limit ? , ?";//开始位置  查询条数

        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid,start,pagesize);
    }
}
