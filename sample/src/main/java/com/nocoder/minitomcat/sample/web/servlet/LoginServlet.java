package com.nocoder.minitomcat.sample.web.servlet;

import com.nocoder.minitomcat.exception.base.ServletException;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import com.nocoder.minitomcat.servlet.impl.HttpServlet;
import com.nocoder.minitomcat.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    
    private UserService userService;

    public LoginServlet() {
        userService = UserService.getInstance();
    }

    @Override
    public void init() {
        logger.info("LoginServlet init...");
    }

    @Override
    public void destroy() {
        logger.info("LoginServlet destroy...");
    }

    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            logger.info("已经登录，跳转至success页面");
            response.sendRedirect("/views/success.html");
        } else {
            request.getRequestDispatcher("/views/login.html").forward(request,response);
        }
    }

    @Override
    public void doPost(Request request, Response response) throws ServletException, IOException {
        Map<String, List<String>> params = request.getParams();
        String username = params.get("username").get(0);
        String password = params.get("password").get(0);
        if (userService.login(username, password)) {
            logger.info("{} 登录成功", username);
            request.getSession().setAttribute("username", username);
            response.sendRedirect("/views/success.html");
        } else {
            logger.info("登录失败");
            response.sendRedirect("/views/errors/400.html");
        }
    }
}
