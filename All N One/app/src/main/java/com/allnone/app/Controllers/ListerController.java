package com.allnone.app.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.allnone.app.Models.Lister;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.Listee;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

/**
 * Created by Adam on 18/08/15.
 * diary class corrosponds to the diary activity.
 * It holds a list of appoinments which populate a listview
 * in the view.
 */
public class ListerController
{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    private final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    private Lister myLister;
    public HttpRequest myHttpClient;
    private Login myLogin = Login.getInstance();
    private Listee myTodo;
    String[] elements;

    {
        elements = new String[]{"intLister_Id", "intLister_ClientId", "strLister_Complete", "strLister_Personal",
                "dteLister_CompleteDate",
                "strLister_Title", "strLister_Description", "strLister_Deadline", "strLister_Category",
                "intLister_LinkId1", "intLister_LinkId2", "intLister_LinkId3", "intLister_LinkId4",
                "strLister_eCourseRef", "intLister_FromId", "strLister_FromDate", "strLister_FromSource",
                "intLister_ProjectId", "intLister_MeetingId", "intLister_MeetingActionPointId"};
    }

    public Lister getLister()
    {
     return myLister;
    }

    public void fn_ListerPOSTRestCall(String ListerFunction, String day) throws ParseException {
        myHttpClient = new HttpRequest();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //region PostParameters
        if (ListerFunction .equals("list_listee_incomplete")) {
            BasicNameValuePair parameter;
            parameter = new BasicNameValuePair("strFunction", "List_Listee_Incomplete");
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
            parameters.add(parameter);
            String clientId = String.valueOf(myLogin.getIntClientid());
            parameter = new BasicNameValuePair("intClient_Id", clientId);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
            parameters.add(parameter);
            myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));
            fn_parseListerXMLContents(fn_createParser
                    (myHttpClient.fnStrGetResponseFromCall()));
        }
        else if (ListerFunction.equals("list_listee_due")) {
            BasicNameValuePair parameter;
            parameter = new BasicNameValuePair("strFunction", ListerFunction);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
            parameters.add(parameter);
            String clientId = String.valueOf(myLogin.getIntClientid());
            parameter = new BasicNameValuePair("intClient_Id", clientId);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strWhen", day);
            parameters.add(parameter);
            myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));
            fn_parseListerXMLContents(fn_createParser
                    (myHttpClient.fnStrGetResponseFromCall()));
        }
        else if(ListerFunction.equals("insert_listee"))
        {
            BasicNameValuePair parameter;
            parameter = new BasicNameValuePair("strFunction", ListerFunction);
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
            parameters.add(parameter);
            parameter = new BasicNameValuePair("intClient_Id", String.valueOf(myLogin.getIntClientid()));
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
            parameters.add(parameter);
            parameter = new BasicNameValuePair("strTitle", day);
            parameters.add(parameter);
            myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));
        }
        //endregion
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
            TimeZone time = TimeZone.getTimeZone("UTC");
            dateFormat.setTimeZone(time);
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
                if (tagName.equals("data")) myLister = new Lister();
                else if (tagName.equals("item")) myTodo = new Listee();
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
           myLister.setStrFunction(text);
        } else if (parser.getName().equals("intErrorId")) {
            int errorId = Integer.parseInt(text);
            myLister.setIntError(errorId);
        } else if (parser.getName().equals("strError")) {
            myLister.setStrError(text);
        } else if (parser.getName().equals(elements[0])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_Id = integer;
        } else if (parser.getName().equals(elements[1])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_ClientId = integer;
        } else if (parser.getName().equals(elements[2])) {
            myTodo.strLister_Complete = text;
        } else if (parser.getName().equals(elements[3])) {
            myTodo.strLister_Personal = text;
        } else if (parser.getName().equals(elements[4])) {
            Date dateEnd = dateFormat2.parse(text);
            myTodo.dteLister_CompleteDate = (dateEnd);
        } else if (parser.getName().equals(elements[5])) {
            myTodo.strLister_Title = text;
        } else if (parser.getName().equals(elements[6])) {
            myTodo.strLister_Description = text;}
        else if (parser.getName().equals(elements[7])) {
            myTodo.strLister_Deadline = text;}
        else if (parser.getName().equals(elements[8])) {
            myTodo.strLister_CategoryPersonal = text;
        } else if (parser.getName().equals(elements[9])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_LinkId1 = integer;
        } else if (parser.getName().equals(elements[10])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_LinkId2= integer;
        } else if (parser.getName().equals(elements[11])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_LinkId3 = integer;
        } else if (parser.getName().equals(elements[12])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_LinkId4 = integer;
        } else if (parser.getName().equals(elements[13])) {
            myTodo.strLister_eCourseRef = text;
        }else if (parser.getName().equals(elements[14])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_FromId = integer;
        } else if (parser.getName().equals(elements[15])) {
            myTodo.strLister_FromDate = text;
        } else if (parser.getName().equals(elements[16])) {
            myTodo.strLister_FromSource = text;
        } else if (parser.getName().equals(elements[17])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_ProjectId = integer;
        } else if (parser.getName().equals(elements[18])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_MeetingId = integer;
        } else if (parser.getName().equals(elements[19])) {
            int integer = Integer.parseInt(text);
            myTodo.intLister_MeetingActionPointId = integer;
        }
        else if (parser.getName().equals("item"))
            myLister.getListees().add(myTodo);
    }
}
