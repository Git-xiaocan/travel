package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    public static JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User FindUserByUserName(String username) {
        String sql = "select * from tab_user where username = ?";
        User user =null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        }catch ( Exception e){

        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone ,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(),user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User FindUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code= ?";
             user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void updateStatus(User u) {
        String sql = "update tab_user set status='"+u.getStatus()+"'where uid=?";
        jdbcTemplate.update(sql,u.getUid());

    }

    @Override
    public User FindUserByUserNameAndPassword(User u) {
        User user = null;
        try {
            String sql = "select * from tab_user where username= ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),u.getUsername(),u.getPassword());
        } catch (DataAccessException e) {

        }
        return user;
    }
}
