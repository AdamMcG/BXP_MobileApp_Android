package com.allnone.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 31/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class Campaign {
    private String strfunction;
    private String strError;
    private int intErrorId;
    private List<CampaignItem> listOfCampaigns = new ArrayList<CampaignItem>();

    //region Accessors
    public String getStrfunction() {
        return strfunction;
    }

    public void setStrfunction(String strfunction) {
        this.strfunction = strfunction;
    }

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

    public List<CampaignItem> getListOfCampaigns() {
        return listOfCampaigns;
    }

    public void setListOfCampaigns(List<CampaignItem> listOfCampaigns) {
        this.listOfCampaigns = listOfCampaigns;
    }
    //endregion


}
