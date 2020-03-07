package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 退出登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exitLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");

    }

    /**
     * 查找登录用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        内部类
        Map<String,Object> map = new HashMap<>();
        if(user!=null){
            map.put("uid", user.getUid());
            map.put("name", user.getName());
            map.put("flag", true);

        }else {

            map.put("flag", false);
        }


        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String check = request.getParameter("check");
        //判断验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //返回结果对象
        ResultInfo resultInfo = new ResultInfo();
        if (check.equalsIgnoreCase(checkcode_server)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            UserService userService = new UserServiceImpl();
            User u = userService.login(user);
            //用户名或者密码错误
            if (u == null) {

                resultInfo.setErrorMsg("用户名或密码错误");
                resultInfo.setFlag(false);
            }
            //账号未激活
            if (u != null && !"Y".equals(u.getStatus())) {

                resultInfo.setErrorMsg("账号未激活，请登录邮箱激活");
                resultInfo.setFlag(false);
            }
            //账号已激活 登录成功
            if (u != null && "Y".equals(u.getStatus())) {
                request.getSession().setAttribute("user", u);
                resultInfo.setFlag(true);
            }
        } else {

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误，请重新输入");
        }
        //响应数据
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), resultInfo);


    }

    /**
     * 用户注册
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //校验验证码
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //获取万立即清除验证码

        ResultInfo resultInfo = new ResultInfo(); //错误信息对象
        if(check==null||check==""||check.equalsIgnoreCase(checkcode_server)){
            //注册成功
            //1.获取数据
            Map<String, String[]> parameterMap = req.getParameterMap();
            //2.封装对象
            User user = new User();

            try {
                BeanUtils.populate(user, parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


            //3.调用service完成注册
            UserService userService = new UserServiceImpl();
            boolean flag = userService.regist(user);

            //4.响应结果

            if (flag) {
                //注册成功
                resultInfo.setFlag(true);
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败");
                //注册失败
            }

        }else{

            //注册失败 验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 用户激活
     * @param req
     * @param resp
     */
    public void active(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        if(code!=null){
            UserService userService = new UserServiceImpl();
            boolean flag =  userService.active(code);
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功,请"+"<a href='../login.html'>登录</a>";


            }else{

                //激活失败
                msg="激活失败，请联系管理员";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);


        }

    }
}
