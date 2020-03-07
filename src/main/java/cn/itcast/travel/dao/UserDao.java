package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    //根据用户名查找
   User FindUserByUserName(String username);
   //保存用户
   void save(User user);
   //根据code查找用户
   User FindUserByCode(String code);
   //更新status
    void updateStatus(User u);
    //通过用户名和密码查找
    User FindUserByUserNameAndPassword(User u);
}
