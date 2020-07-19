package com.nocoder.minitomcat.sample.web.servlet;

import com.nocoder.minitomcat.exception.base.ServletException;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import com.nocoder.minitomcat.servlet.impl.HttpServlet;
import com.nocoder.minitomcat.sample.domain.User;
import com.nocoder.minitomcat.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Slf4j
public class UserEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserEditServlet.class);

    private UserService userService;

    public UserEditServlet() {
        userService = UserService.getInstance();
    }

    
    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("user",user);
        request.getRequestDispatcher("/views/userEdit.html").forward(request,response);
    }

    @Override
    public void doPost(Request request, Response response) throws ServletException, IOException {
        logger.info("{}",request.getParams());
        User user = new User();
        user.setUsername((String) request.getSession(false).getAttribute("username"));
        user.setRealName(request.getParameter("realName"));
        user.setAge(Integer.valueOf(request.getParameter("age")));
        userService.update(user);
        
        request.setAttribute("user",user);
        request.getRequestDispatcher("/views/user.html").forward(request, response);
    }
}
