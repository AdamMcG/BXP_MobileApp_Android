package com.allnone.app.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adam on 06/08/15.
 diary class corresponds to the diary activity.
 It holds a list of appointments which populate a ListView
 in the view.
 */
public class diary implements Serializable {

    public ArrayList<Appointment> colAppointment = new ArrayList<Appointment>();
    private String strError;
    private int intErrorId;
    private String strFunction;
    private Date dtePeriodStart;
    private Date dtePeriodEnd;

    //region AccessorMethods
    public String getStrFunction() {
        return strFunction;
    }

    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    public int getIntErrorId() {
        return intErrorId;
    }

    public void setIntErrorId(int intErrorId) {
        this.intErrorId = intErrorId;
    }
    //endregion

    public Date getDtePeriodStart() {
        return dtePeriodStart;
    }

    public void setDtePeriodStart(Date dtePeriodStart) {
        this.dtePeriodStart = dtePeriodStart;
    }

    public Date getDtePeriodEnd() {
        return dtePeriodEnd;
    }

    public void setDtePeriodEnd(Date dtePeriodEnd) {
        this.dtePeriodEnd = dtePeriodEnd;
    }

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }
}
