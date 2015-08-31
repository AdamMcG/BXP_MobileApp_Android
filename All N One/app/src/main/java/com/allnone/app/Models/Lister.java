package com.allnone.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 18/08/15.
 * diary class corresponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class Lister
{
    //region Accessor Methods

    //endregion
    private List<Listee> listees = new ArrayList<Listee>();
    private String strFunction;
    private int intError;
    private String strError;

    public String getStrError() {
        return strError;
    }

    public void setStrError(String strError) {
        this.strError = strError;
    }

    public int getIntError() {
        return intError;
    }

    public void setIntError(int intError) {
        this.intError = intError;
    }

    public String getStrFunction() {
        return strFunction;
    }

    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    public List<Listee> getListees() {
        return listees;
    }

    public void setListees(List<Listee> listees) {
        this.listees = listees;
    }



}
