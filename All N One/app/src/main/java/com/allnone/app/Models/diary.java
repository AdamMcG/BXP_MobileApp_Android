package com.allnone.app.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adam on 06/08/15.
 diary class corrosponds to the diary activity.
 It holds a list of appoinments which populate a listview
 in the view.
 */
public class diary implements Serializable {
    private int intErrorId;
    private String strFunction;
    private Date dtePeriodStart;
    private Date dtePeriodEnd;
    private ArrayList<Appointment> colAppointment = new ArrayList<Appointment>();
}
