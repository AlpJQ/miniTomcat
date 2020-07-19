package com.nocoder.minitomcat.sample.web.servlet;

import com.nocoder.minitomcat.exception.base.ServletException;
import com.nocoder.minitomcat.request.Request;
import com.nocoder.minitomcat.response.Response;
import com.nocoder.minitomcat.servlet.impl.HttpServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LogoutServlet extends HttpServlet {
    
    @Override
    public void doGet(Request request, Response response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/logout.html").forward(request,response);  
    }

    @Override
    public void doPost(Request request, Response response) throws ServletException, IOException {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        response.sendRedirect("/login");
    }
}
