package com.allnone.app.Models;

/**
 * Created by Adam on 31/08/15.
 * this class contains the settings for buttons in the system.
 */
public class Buttons {
    private int intErrorId;
    private String strError;
    private String strFunction;
    private String strInterface_Button_Title;
    private String strInterface_Button_Styling;
    private String StrInterface_Button_Layout;
    private String strInterface_Button_FunctionType;
    private String strInterface_Button_APICall;
    private String strInterface_Button_Config;
    private String strInterface_Button_URL;
    private String strInterface_Button_Parameters;

    //region buttonSettings
    public String getStrInterface_Button_Styling() {
        return strInterface_Button_Styling;
    }

    public void setStrInterface_Button_Styling(String strInterface_Button_Styling) {
        this.strInterface_Button_Styling = strInterface_Button_Styling;
    }

    public String getStrInterface_Button_Title() {
        return strInterface_Button_Title;
    }

    public void setStrInterface_Button_Title(String strInterface_Button_Title) {
        this.strInterface_Button_Title = strInterface_Button_Title;
    }

    public String getStrInterface_Button_Layout() {
        return StrInterface_Button_Layout;
    }

    public void setStrInterface_Button_Layout(String strInterface_Button_Layout) {
        StrInterface_Button_Layout = strInterface_Button_Layout;
    }

    public String getStrInterface_Button_FunctionType() {
        return strInterface_Button_FunctionType;
    }

    public void setStrInterface_Button_FunctionType(String strInterface_Button_FunctionType) {
        this.strInterface_Button_FunctionType = strInterface_Button_FunctionType;
    }

    public String getStrInterface_Button_APICall() {
        return strInterface_Button_APICall;
    }

    public void setStrInterface_Button_APICall(String strInterface_Button_APICall) {
        this.strInterface_Button_APICall = strInterface_Button_APICall;
    }

    public String getStrInterface_Button_Config() {
        return strInterface_Button_Config;
    }

    public void setStrInterface_Button_Config(String strInterface_Button_Config) {
        this.strInterface_Button_Config = strInterface_Button_Config;
    }

    public String getStrInterface_Button_URL() {
        return strInterface_Button_URL;
    }

    public void setStrInterface_Button_URL(String strInterface_Button_URL) {
        this.strInterface_Button_URL = strInterface_Button_URL;
    }

    public String getStrInterface_Button_Parameters() {
        return strInterface_Button_Parameters;
    }

    public void setStrInterface_Button_Parameters(String strInterface_Button_Parameters) {
        this.strInterface_Button_Parameters = strInterface_Button_Parameters;
    }
    //endregion

    //region Accessor Method
    public int getIntErrorId() {
        return intErrorId;
    }

    public void setIntErrorId(int intErrorId) {
        this.intErrorId = intErrorId;
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }

    public String getStrFunction() {
        return strFunction;
    }

    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }
    //endregion
}
