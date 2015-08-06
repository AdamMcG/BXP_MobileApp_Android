package com.allnone.app.Models;

/**
 * Created by testing on 06/08/15.
 */
public class Login {

    private int intClientid;
    private String strClient_SessionField;
    private String strError;
    private String strUserName;
    private String strPassword;
    private String strSystemUsed;
    private String strFunction;
    private static Login instance;

    public static Login getInstance()
    {
        if (instance == null)
        {
            instance = new Login();
        }

        return instance;
    }
    public Login()
    {}

    public Login(String system, String username, String password, String function, String sessionField, int clientID,
                 String error)
    {
        getInstance();
        instance.intClientid = clientID;
        instance.strClient_SessionField = sessionField;
        instance.strError = error;
        instance.strSystemUsed = system;
        instance.strFunction = function;
        instance.strUserName = username;
        instance.strPassword = password;

    }
    
}
