package com.allnone.app.Controllers;

import com.allnone.app.Models.Hamster;
import com.allnone.app.Models.ListOfHamsters;
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
 * Created by Adam on 18/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class HamsterController
{
    public HttpRequest myHttp;
    String[] elements = {"green", "jade", "emerald", "apple", "help", "white", "olive", "lime", "suggestion", "newUser", "excellent",
            "good", "poor", "awful"};
    private Login myLogin = Login.getInstance();
    private ListOfHamsters hamLists;

    public ListOfHamsters getHamLists() {
        return hamLists;
    }

    public void fn_HamsterRESTPostCall() {
        myHttp = new HttpRequest();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        BasicNameValuePair parameter = new BasicNameValuePair("strFunction", "list_hamsters");
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
        parameters.add(parameter);
        parameter = new BasicNameValuePair("intClient_Id", String.valueOf(myLogin.getIntClientid()));
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
        parameters.add(parameter);
        myHttp.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttp.fnStrSettingParameters(parameters));
    }

    public XmlPullParser fn_createParser(String xmlString) {
        XmlPullParser parser = null;
        try {
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(xmlString));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void fn_parseListerXMLContents(XmlPullParser parser) throws ParseException {
        try {
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                text = fn_parsingListerContent(parser, text, eventType, tagName);
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fn_parsingListerContent(XmlPullParser parser, String text, int eventType, String tagName) throws ParseException {
        switch (eventType) {
            case START_TAG:
                if (tagName.equals("data"))
                    hamLists = new ListOfHamsters();
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

    private void decidingText(XmlPullParser parser, String text) throws ParseException {
        if (parser.getName().equals("strFunction")) {
            hamLists.setStrFunction(text);
        } else if (parser.getName().equals("intErrorId")) {
            int errorId = Integer.parseInt(text);
            hamLists.setIntError(errorId);
        } else if (parser.getName().equals("strError")) {
           hamLists.setStrError(text);
        }
        for (String element : elements)
            if (parser.getName().equals(element)) {
                Hamster objHamster = new Hamster();
                objHamster.setName(element);
                objHamster.setDetail(Integer.parseInt(text));
                hamLists.getListOfHamsters().add(objHamster);
            }
    }

}
