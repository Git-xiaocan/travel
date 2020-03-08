package test.cn.itcast.travel.dao.impl; 

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* RouteDaoImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>3月 7, 2020</pre> 
* @version 1.0 
*/ 
public class RouteDaoImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findtotalCount(int cid, String rname) 
* 
*/ 
@Test
public void testFindtotalCount() throws Exception {
    RouteDao routeDao = new RouteDaoImpl();
    int count = routeDao.findtotalCount(5,"兵马俑");
    System.out.println(count);
} 

/** 
* 
* Method: findByPage(int cid, int start, int pagesize, String rname) 
* 
*/ 
@Test
public void testFindByPage() throws Exception { 
//TODO: Test goes here... 
} 


} 
