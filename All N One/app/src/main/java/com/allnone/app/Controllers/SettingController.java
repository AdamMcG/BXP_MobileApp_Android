package com.allnone.app.Controllers;

import com.allnone.app.Models.Buttons;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.Setting;

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
 * Class corresponds to manipulating data in the settings class.
 */
public class SettingController {
    Login myLogin = Login.getInstance();
    Setting mySetting = new Setting();
    Buttons myButton = new Buttons();


    public void fn_SettingsPostCall(String strRestFunction) {
        HttpRequest myHttp = new HttpRequest();

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", "config_primary");
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
        parameters.add(parameter);
        String clientId = String.valueOf(myLogin.getIntClientid());
        parameter = new BasicNameValuePair("intClient_Id", clientId);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
        parameters.add(parameter);

        String setParams = myHttp.fnStrSettingParameters(parameters);
        myHttp.fn_BxpApi_PostCall(strRestFunction, setParams);
        XmlPullParser myparser = createParser(myHttp.fnStrGetResponseFromCall());
        fn_parsePrimaryXMLcontents(myparser);
    }

    public void fn_ButtonsPostCall(String strRestFunction, int numberOfButtons) {
        HttpRequest myHttp = new HttpRequest();
        for (int i = 0; i < numberOfButtons; i++) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            BasicNameValuePair parameter;
            parameter = new BasicNameValuePair("strFunction", "config_button" + i);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
            parameters.add(parameter);
            String clientId = String.valueOf(myLogin.getIntClientid());
            parameter = new BasicNameValuePair("intClient_Id", clientId);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
            parameters.add(parameter);

            String setParams = myHttp.fnStrSettingParameters(parameters);
            myHttp.fn_BxpApi_PostCall(strRestFunction, setParams);
            XmlPullParser myparser = createParser(myHttp.fnStrGetResponseFromCall());
            parsebuttonConfigXML(myparser, i);
        }
    }


    public void parsebuttonConfigXML(XmlPullParser parser, int number) {
        try {
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                text = fn_parsingConfigbutton(parser, text, eventType, tagName, number);
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

    public XmlPullParser createParser(String xmlString) {
        XmlPullParser parser = null;
        try {
            XmlPullParserFactory myFactory = XmlPullParserFactory.newInstance();
            myFactory.setNamespaceAware(true);
            parser = myFactory.newPullParser();
            parser.setInput(new StringReader(xmlString));

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void fn_parsePrimaryXMLcontents(XmlPullParser parser) {
        try {
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                text = fn_parsingConfigPrimary(parser, text, eventType, tagName);
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

    private String fn_parsingConfigPrimary(XmlPullParser parser, String text, int eventType, String tagName) throws ParseException {
        switch (eventType) {
            case START_TAG:
                if (tagName.equals("data")) {
                    int a = 3;
                }
            case TEXT:
                text = parser.getText();
                break;
            case END_TAG:
                decidingText(parser, text);
                break;
            default:
                break;
        }
        return text;
    }

    private String fn_parsingConfigbutton(XmlPullParser parser, String text, int eventType, String tagName, int number) throws ParseException {
        switch (eventType) {
            case START_TAG:
                if (tagName.equals("data")) {
                    int a = 3;
                }
            case TEXT:
                text = parser.getText();
                break;
            case END_TAG:
                buttonDecidingText(parser, text, number);
                break;
            default:
                break;
        }
        return text;
    }


    private void buttonDecidingText(XmlPullParser parser, String text, int number) {
        String parserCheck = "strInterface_Button" + number;
        if (parser.getName().equals("strFunction")) myButton.setStrFunction(text);
        else if (parser.getName().equals("intErrorId")) {
            int errorId = Integer.parseInt(text);
            myButton.setIntErrorId(errorId);
        } else if (parser.getName().equals("strError")) myButton.setStrError(text);
        else if (parser.getName().equals(parserCheck + "_Title"))
            myButton.setStrInterface_Button_Title(text);
        else if (parser.getName().equals(parserCheck + "_Styling"))
            myButton.setStrInterface_Button_Styling(text);
        else if (parser.getName().equals(parserCheck + "_Layout"))
            myButton.setStrInterface_Button_Layout(text);
        else if (parser.getName().equals(parserCheck + "_FunctionType"))
            myButton.setStrInterface_Button_FunctionType(text);
        else if (parser.getName().equals(parserCheck + "_APICall"))
            myButton.setStrInterface_Button_APICall(text);
        else if (parser.getName().equals(parserCheck + "_Config"))
            myButton.setStrInterface_Button_Config(text);
        else if (parser.getName().equals(parserCheck + "_URL"))
            myButton.setStrInterface_Button_URL(text);
        else if (parser.getName().equals(parserCheck + "_Parameters"))
            myButton.setStrInterface_Button_Parameters(text);
        else if (parser.getName().equals("data"))
            mySetting.myListOfButtons.add(myButton);
    }


    private void decidingText(XmlPullParser parser, String text) throws ParseException {
        if (parser.getName().equals("strFunction")) mySetting.setStrFunction(text);
        else if (parser.getName().equals("intErrorId")) {
            int errorId = Integer.parseInt(text);
            mySetting.setIntErrorId(errorId);
        } else if (parser.getName().equals("strError")) mySetting.setStrError(text);
        else if (parser.getName().equals("intInterface_SystemId")) {
            int errorId = Integer.parseInt(text);
            mySetting.setIntInterface_SystemId(errorId);
        } else if (parser.getName().equals("intInterface_UserId")) {
            int errorId = Integer.parseInt(text);
            mySetting.setIntInterface_SystemId(errorId);
        } else if (parser.getName().equals("intInterface_Columns")) {
            int errorId = Integer.parseInt(text);
            mySetting.setIntInterface_Columns(errorId);
        } else if (parser.getName().equals("strInterface_RSSTitle"))
            mySetting.setStrInterface_RSSTitle(text);
        else if (parser.getName().equals("strInterface_RssFeed"))
            mySetting.setStrInterface_RSSFeed(text);
        else if (parser.getName().equals("strInterface_StoreSystemAndUsername"))
            mySetting.setBoolInterface_StoreUsername(Boolean.parseBoolean(text));
        else if (parser.getName().equals("strInterface_Image_Logo"))
            mySetting.setStrInterface_Image_LogoURL(text);
        else if (parser.getName().equals("strInterface_Image_Background"))
            mySetting.setStrInterface_Image_Background(text);
        else if (parser.getName().equals("strInterface_FontColours"))
            mySetting.setStrInterface_FontColours(text);
        else if (parser.getName().equals("strInterface_FontFaces"))
            mySetting.setStrInterface_FontFaces(text);
        else if (parser.getName().equals("strInterface_FontSizes"))
            mySetting.setStrInterface_FontSizes(text);
        else if (parser.getName().equals("strInterface_SystemKeywords"))
            mySetting.setStrInterface_SystemKeywords(text);

    }
}
