package com.allnone.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 18/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class ListOfHamsters
{
    private String strFunction;
    private String strError;
    private int intError;

    //region Accessor Methods
    public List<Hamster> getListOfHamsters() {
        return ListOfHamsters;
    }

    public void setListOfHamsters(List<Hamster> listOfHamsters) {
        ListOfHamsters = listOfHamsters;
    }

    public int getIntError() {
        return intError;
    }

    public void setIntError(int intError) {
        this.intError = intError;
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

    private List<Hamster> ListOfHamsters = new ArrayList<Hamster>();
}
