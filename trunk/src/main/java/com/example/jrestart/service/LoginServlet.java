package com.example.jrestart.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)

            throws ServletException, IOException {

        //add to UserSessions singleton instance
        String sessionId = request.getSession().getId();
        String user = "Alex";
        request.getSession().setAttribute("user", user);
        UserSessions.getInstance().addUserSession(sessionId, user);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
                "<BODY>\n" +
                "<H3>Hello " +
                user +
                "!</H3>\n" +
                "</BODY></HTML>");
    }
}