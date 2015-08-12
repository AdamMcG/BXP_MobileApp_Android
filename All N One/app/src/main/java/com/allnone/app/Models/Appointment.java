package com.allnone.app.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by testing on 06/08/15.
 * This class deals with appointment objects contained within a diary object.
 * They populate the appointmentlistviews used in the Today and Tomorrow activities.
 */
public class Appointment {
    private int intAppointmentid;
    private String strAppointmentTitle;
    private Date dteAppointmentStart;
    private Date getDteAppointmentEnd;
    private String strUrl;

    public int getIntAppointmentid() {
        return intAppointmentid;
    }

    public void setIntAppointmentid(int intAppointmentid) {
        this.intAppointmentid = intAppointmentid;
    }

    public String getStrAppointmentTitle() {
        return strAppointmentTitle;
    }

    public void setStrAppointmentTitle(String strAppointmentTitle) {
        this.strAppointmentTitle = strAppointmentTitle;
    }

    public Date getDteAppointmentStart() {
        return dteAppointmentStart;
    }

    public void setDteAppointmentStart(Date dteAppointmentStart) {
        this.dteAppointmentStart = dteAppointmentStart;
    }

    public Date getGetDteAppointmentEnd() {
        return getDteAppointmentEnd;
    }

    public void setGetDteAppointmentEnd(Date getDteAppointmentEnd) {
        this.getDteAppointmentEnd = getDteAppointmentEnd;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public String returnDateStartString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'T' hh:mm:ss");
        TimeZone time = TimeZone.getTimeZone("UTC");
        dateFormat.setTimeZone(time);
        return dateFormat.format(getDteAppointmentStart());
    }

    public String toString() {
        return String.format("%d: %s", getIntAppointmentid(), getStrAppointmentTitle());
    }
}
