package com.allnone.app.Models;

/**
 * Created by testing on 06/08/15.
 * Login:
 * This class deals with storage of login and session credentials for usage
 * with the other function calls.
 */
public class Login {

    private static Login instance;
    private int intClientid;
    private int intError;
    private String strUrlUsed;
    private String strPassword;
    private String strSystemUsed;
    private String strClient_SessionField;
    private String strError;
    private String strUserName;
    private String strFunction;


    public Login() {

    }

    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }

        return instance;
    }

    //region AccessorMethods
    public String getStrUrlUsed() {
        return strUrlUsed;
    }

    public void setStrUrlUsed(String strUrlUsed) {
        this.strUrlUsed = strUrlUsed;
    }

    public int getIntError() {
        return intError;
    }

    public void setIntError(int intError) {
        this.intError = intError;
    }
    public int getIntClientid() {
        return intClientid;
    }
    public void setIntClientid(int intClientid) {
        this.intClientid = intClientid;
    }
    public String getStrClient_SessionField() {
        return strClient_SessionField;
    }
    public void setStrClient_SessionField(String strClient_SessionField) {
        this.strClient_SessionField = strClient_SessionField;
    }
    public String getStrError() {
        return strError;
    }
    public void setStrError(String strError) {
        this.strError = strError;
    }
    public String getStrUserName() {
        return strUserName;
    }
    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }
    public String getStrPassword() {
        return strPassword;
    }
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }
    public String getStrSystemUsed() {
        return strSystemUsed;
    }
    public void setStrSystemUsed(String strSystemUsed) {
        this.strSystemUsed = strSystemUsed;
    }
    public String getStrFunction() {
        return strFunction;
    }
    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }
    //endregion
}
