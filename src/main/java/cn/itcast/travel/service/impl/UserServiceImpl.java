package cn.itcast.travel.service.impl;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * 用户注册sercie 实体类
 */
public class UserServiceImpl implements UserService {
    private static UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        if (userDao.FindUserByUserName(user.getUsername()) == null) {
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");

            userDao.save(user);
            //邮件激活
            String content = "<a href=\"http://127.0.0.1/travel/user/active?code=" + user.getCode() + "\">点击激活</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活邮件");
            return true;


        }

        return false;
    }

    @Override
   public boolean active(String code) {
        if (code != null) {
           User user =  userDao.FindUserByCode(code);
           if(user ==null){
               return false;
           }
           user.setStatus("Y");
           userDao.updateStatus(user);
            return true;

        }

    return false;
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {

        return userDao.FindUserByUserNameAndPassword(user);
    }
}
