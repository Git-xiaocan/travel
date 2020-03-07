package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    //从redis查询

    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {

        //从redis查询
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeByScoreWithScores("category", 0, -1);
        List<Category> cs = null;
        if (category == null || category.size() == 0) {
            //从数据库查询
            cs = categoryDao.findAll();
            //将数据存入redis 中
            for ( int i =0 ;i < cs.size() ;i ++){

                jedis.zadd("category", cs.get(i).getCid(),cs.get(i).getCname());

            }

        }else{
            //将set存入list
            cs = new ArrayList<Category>();

            for (Tuple name : category) {
                Category c = new Category();

                c.setCname(name.getElement());
                c.setCid((int)name.getScore());
                cs.add(c);

            }
        }
        return cs;
    }
}
