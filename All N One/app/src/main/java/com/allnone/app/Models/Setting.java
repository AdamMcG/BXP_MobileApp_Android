package com.allnone.app.Models;

/**
 * Created by testing on 06/08/15.
 * This class contains and stores the session settings and should persist any changes in the app.
 */
public class Setting {
    private int intErrorId;
    private String strError;

    //region Accessor Methods
    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }

    public int getIntErrorId() {
        return intErrorId;
    }

    public void setIntErrorId(int intErrorId) {
        this.intErrorId = intErrorId;
    }
    //endregion
}
