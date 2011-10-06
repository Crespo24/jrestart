package com.example.jrestart.service;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

public class MySessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se)
    {
        HttpSession session = se.getSession();
        System.out.print(getTime() + " (session) Created:");
        System.out.println("ID=" + session.getId() + " MaxInactiveInterval=" + session.getMaxInactiveInterval());
    }
    public void sessionDestroyed(HttpSessionEvent se)
    {
        HttpSession session = se.getSession();

        //remove from user sessions
        String sessionId = session.getId();
        System.out.println("Removing user: "+session.getAttribute("user"));
        UserSessions.getInstance().removeUserSession(sessionId);

        // session has been invalidated and all session data
        //(except Id)is no longer available
        System.out.println(getTime() + " (session) Destroyed:ID=" + session.getId());
    }

    private String getTime()
    {
        return new Date(System.currentTimeMillis()).toString();
    }

}