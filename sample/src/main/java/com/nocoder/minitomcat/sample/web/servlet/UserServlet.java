package com.nocoder.minitomcat.sample.web.servlet;

import com.nocoder.minitomcat.exception.base.ServletException;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import com.nocoder.minitomcat.servlet.impl.HttpServlet;
import com.nocoder.minitomcat.sample.domain.User;
import com.nocoder.minitomcat.sample.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserServlet extends HttpServlet {
    private UserService userService;

    public UserServlet() {
        userService = UserService.getInstance();
    }
    
    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        User user = userService.findByUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("user",user);
        request.getRequestDispatcher("/views/user.html").forward(request, response);
    }
}
