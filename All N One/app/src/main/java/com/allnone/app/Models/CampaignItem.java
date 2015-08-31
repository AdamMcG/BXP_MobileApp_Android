package com.allnone.app.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 31/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class CampaignItem {
    public List<String> itemsOfCampaign = new ArrayList<String>();
    private int intId;

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }
}
