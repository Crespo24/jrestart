package com.example.jrestart.service;

import java.util.HashMap;
import java.util.Map;

public final class UserSessions {

    /**
     * Map<sessionID, userId>
     */
    private Map<String, String> sessionIds;

    private static UserSessions instance;

    private UserSessions() {
        sessionIds = new HashMap<String, String>();
    }

    public static UserSessions getInstance() {
        if (instance == null) {
            instance = new UserSessions();
        }
        return instance;
    }

    public void addUserSession(String sessionId, String user) {
        sessionIds.put(sessionId, user);
    }

    public void removeUserSession(String sessionId) {
        sessionIds.remove(sessionId);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}