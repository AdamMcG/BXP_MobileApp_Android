package com.allnone.app.Models;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by testing on 06/08/15.
 * This class contains and stores the session settings and should persist any changes in the app.
 */
public class Setting {
    private static int intErrorId;
    private static String strError;
    private static String strFunction;
    private static int intInterface_SystemId;
    private static int intInterface_UserId;
    private static int intInterface_Columns;
    private static String strInterface_RSSFeed;
    private static String strInterface_RSSTitle;
    private static boolean boolInterface_StoreUsername;
    private static String strInterface_Image_LogoURL;
    private static String strInterface_Image_Background;
    private static String strInterface_FontColours;
    private static String strInterface_FontSizes;
    private static String strInterface_FontFaces;
    public List<Buttons> myListOfButtons = new ArrayList<Buttons>();
    private String strInterface_SystemKeywords;

    //region Accessor Methods
    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        Setting.strError = strError;
    }

    public int getIntErrorId() {
        return intErrorId;
    }

    public void setIntErrorId(int intErrorId) {
        Setting.intErrorId = intErrorId;
    }

    public String getStrInterface_SystemKeywords() {
        return strInterface_SystemKeywords;
    }

    public void setStrInterface_SystemKeywords(String strInterface_SystemKeywords) {
        this.strInterface_SystemKeywords = strInterface_SystemKeywords;
    }

    public String getStrInterface_FontFaces() {
        return strInterface_FontFaces;
    }

    public void setStrInterface_FontFaces(String strInterface_FontFaces) {
        Setting.strInterface_FontFaces = strInterface_FontFaces;
    }

    public String getStrInterface_FontSizes() {
        return strInterface_FontSizes;
    }

    public void setStrInterface_FontSizes(String strInterface_FontSizes) {
        Setting.strInterface_FontSizes = strInterface_FontSizes;
    }

    public String getStrInterface_FontColours() {
        return strInterface_FontColours;
    }

    public void setStrInterface_FontColours(String strInterface_FontColours) {
        Setting.strInterface_FontColours = strInterface_FontColours;
    }

    public String getStrInterface_Image_Background() {
        return strInterface_Image_Background;
    }

    public void setStrInterface_Image_Background(String strInterface_Image_Background) {
        Setting.strInterface_Image_Background = strInterface_Image_Background;
    }

    public String getStrInterface_Image_LogoURL() {
        return strInterface_Image_LogoURL;
    }

    public void setStrInterface_Image_LogoURL(String strInterface_Image_LogoURL) {
        Setting.strInterface_Image_LogoURL = strInterface_Image_LogoURL;
    }

    public boolean isBoolInterface_StoreUsername() {
        return boolInterface_StoreUsername;
    }

    public void setBoolInterface_StoreUsername(boolean boolInterface_StoreUsername) {
        Setting.boolInterface_StoreUsername = boolInterface_StoreUsername;
    }

    public String getStrInterface_RSSTitle() {
        return strInterface_RSSTitle;
    }

    public void setStrInterface_RSSTitle(String strInterface_RSSTitle) {
        Setting.strInterface_RSSTitle = strInterface_RSSTitle;
    }

    public String getStrInterface_RSSFeed() {
        return strInterface_RSSFeed;
    }

    public void setStrInterface_RSSFeed(String strInterface_RSSFeed) {
        Setting.strInterface_RSSFeed = strInterface_RSSFeed;
    }

    public int getIntInterface_Columns() {
        return intInterface_Columns;
    }

    public void setIntInterface_Columns(int intInterface_Columns) {
        Setting.intInterface_Columns = intInterface_Columns;
    }

    public int getIntInterface_UserId() {
        return intInterface_UserId;
    }

    public void setIntInterface_UserId(int intInterface_UserId) {
        Setting.intInterface_UserId = intInterface_UserId;
    }

    public int getIntInterface_SystemId() {
        return intInterface_SystemId;
    }

    public void setIntInterface_SystemId(int intInterface_SystemId) {
        Setting.intInterface_SystemId = intInterface_SystemId;
    }

    public String getStrFunction() {
        return strFunction;
    }

    public void setStrFunction(String strFunction) {
        Setting.strFunction = strFunction;
    }
    //endregion
}
