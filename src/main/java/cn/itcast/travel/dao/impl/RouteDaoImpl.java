package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findtotalCount(int cid, String rname) {
        StringBuffer sql = new StringBuffer("select count(*) from tab_route where 1=1 ");

        List parameter = new ArrayList();
        if (cid != 0) {
            sql.append(" and cid = ? ");
            parameter.add(cid);

        }
        if (rname != null && rname != "" && rname != "null" & rname.length() > 0) {

            sql.append(" and rname like ? ");
            rname = "%" + rname + "%";

            parameter.add(rname);
        }

        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, parameter.toArray());




    }

    @Override
    public List<Route> findByPage(int cid, int start, int pagesize, String rname) {
        StringBuffer sql = new StringBuffer("select * from tab_route where 1=1 ");


        List parameter = new ArrayList();
        if (cid != 0) {
            sql.append("and cid= ? ");
            parameter.add(cid);

        }
        if (rname != null && rname != "" && rname.length() > 0 && rname != "null") {
            sql.append("and rname like ? ");
            parameter.add("%" + rname + "%");
        }
        parameter.add(start);
        parameter.add(pagesize);
        sql.append("limit ?,? ");

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<Route>(Route.class), parameter.toArray());


    }
}
