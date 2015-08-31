package com.allnone.app.Controllers;


import com.allnone.app.Models.Campaign;
import com.allnone.app.Models.CampaignItem;
import com.allnone.app.Models.Login;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

/**
 * Created by Adam on 31/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class CampaignController {
    Campaign myCampaign;
    CampaignItem campaignItemInstance;
    Login myLogin = Login.getInstance();
    int formToLookup = 0;
    String returnField;
    //endregion
    String returnField2;
    String returnField3;
    String returnField4;

    //region Campaigns
    public CampaignItem getCampaignItemInstance() {
        return campaignItemInstance;
    }

    public void setCampaignItemInstance(CampaignItem campaignItemInstance) {
        this.campaignItemInstance = campaignItemInstance;
    }

    public Campaign getMyCampaign() {
        return myCampaign;
    }

    public void setMyCampaign(Campaign myCampaign) {
        this.myCampaign = myCampaign;
    }

    public void fn_RetrieveCampaignItemsByFirstName(String searchItem, int campaignId) {
        formToLookup = campaignId;
        returnField = "strCDA_" + formToLookup + "_field_1_15";
        returnField2 = "strCDA_" + formToLookup + "_field_3_15";
        returnField3 = "strCDA_" + formToLookup + "_field_0_4";
        returnField4 = "strCDA_" + formToLookup + "_field_0_6";
        String returnFields = returnField + "," + returnField2 + "," + returnField3 + "," + returnField4;
        HttpRequest myHttpClient = new HttpRequest();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //region PostParameters
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "formlookup");
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
        parameters.add(parameter);
        String clientId = String.valueOf(myLogin.getIntClientid());
        parameter = new BasicNameValuePair("intClient_ID", clientId);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
        parameters.add(parameter);
        String a = "" + campaignId;
        parameter = new BasicNameValuePair("intCampaign_Id", a);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSearch_Field", returnField);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSearch_Value", searchItem);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strReturn_Fields", returnFields);
        parameters.add(parameter);
        //endregion
        myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));
        XmlPullParser mySetupParser = parseThroughCampaignItems(myHttpClient.fnStrGetResponseFromCall());
        PullingDataFromXML(mySetupParser);
    }

    public void fn_RetrieveCampaignItemsByLastName(String searchItem, int campaignId) {
        String returnFields = returnField + "," + returnField2 + "," + returnField3 + "," + returnField4;
        formToLookup = campaignId;
        HttpRequest myHttpClient = new HttpRequest();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //region PostParameters
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "formlookup");
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
        parameters.add(parameter);
        String clientId = String.valueOf(myLogin.getIntClientid());
        parameter = new BasicNameValuePair("intClient_ID", clientId);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
        parameters.add(parameter);
        String a = "" + campaignId;
        parameter = new BasicNameValuePair("intCampaign_Id", a);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSearch_Field", returnField2);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSearch_Value", searchItem);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strReturn_Fields", returnFields);
        parameters.add(parameter);
        //endregion
        myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));
        parseThroughCampaignItems(myHttpClient.fnStrGetResponseFromCall());
    }

    public XmlPullParser parseThroughCampaignItems(String campaignXMLString) {
        XmlPullParser parser = null;
        try {
            XmlPullParserFactory myFac = XmlPullParserFactory.newInstance();
            myFac.setNamespaceAware(true);
            parser = myFac.newPullParser();
            parser.setInput(new StringReader(campaignXMLString));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void PullingDataFromXML(XmlPullParser parser) {
        try {
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                text = ParsingCampaignBuild(parser, text, eventType, tagName);
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String ParsingCampaignBuild(XmlPullParser parser, String text, int eventType, String tagName) throws ParseException {
        switch (eventType) {
            case START_TAG:
                if (tagName.equals("data")) {
                    myCampaign = new Campaign();
                } else if (tagName.equals("item")) {
                    campaignItemInstance = new CampaignItem();
                }
            case TEXT:
                text = parser.getText();
                break;
            case END_TAG:
                decidingCampaignText(parser, text);
                break;
            default:
                break;
        }
        return text;
    }

    public void decidingCampaignText(XmlPullParser parser, String text) {
        if (parser.getName().equals("strFunction"))
            myCampaign.setStrfunction(text);
        else if (parser.getName().equals("intErrorId")) {
            int errorId = Integer.parseInt(text);
            myCampaign.setIntErrorId(errorId);
        } else if (parser.getName().equals("strError"))
            myCampaign.setStrError(text);
        else if (parser.getName().equals("intId")) {
            int id = Integer.parseInt(text);
            campaignItemInstance.setIntId(id);
        } else if (parser.getName().equals(returnField))
            campaignItemInstance.itemsOfCampaign.add(text);
        else if (parser.getName().equals(returnField2))
            campaignItemInstance.itemsOfCampaign.add(text);
        else if (parser.getName().equals(returnField3))
            campaignItemInstance.itemsOfCampaign.add(text);
        else if (parser.getName().equals(returnField4))
            campaignItemInstance.itemsOfCampaign.add(text);
        else if (parser.getName().equals("item"))
            myCampaign.getListOfCampaigns().add(campaignItemInstance);
    }
}
