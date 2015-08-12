package com.allnone.app.Controllers;

import com.allnone.app.Models.Appointment;
import com.allnone.app.Models.Login;
import com.allnone.app.Models.diary;

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
 * Created by Adam on 11/08/15.
 * This controller class deals with functionality within the diary activities:
 * The today page and the tomorrow page.
 */
public class DiaryController {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    Login myLogin = Login.getInstance();
    private HttpRequest myHttpClient;
    private diary myDiary;
    private Appointment myAppointment;

    public diary getMyDiary() {
        return myDiary;
    }

    public void fn_diaryRestCallPost(String diaryFunction) throws ParseException {
        myHttpClient = new HttpRequest();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //region PostParameters
        BasicNameValuePair parameter;
        parameter = new BasicNameValuePair("strFunction", diaryFunction);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strSystem", myLogin.getStrSystemUsed());
        parameters.add(parameter);
        String clientId = String.valueOf(myLogin.getIntClientid());
        parameter = new BasicNameValuePair("intClient_ID", clientId);
        parameters.add(parameter);
        parameter = new BasicNameValuePair("strClient_SessionField", myLogin.getStrClient_SessionField());
        parameters.add(parameter);
        parameter = new BasicNameValuePair("intDiary", "3");
        parameters.add(parameter);
        //endregion
        myHttpClient.fn_BxpApi_PostCall(myLogin.getStrUrlUsed(), myHttpClient.fnStrSettingParameters(parameters));

        fn_parseDiaryXMLContents(fn_createParser(myHttpClient.fnStrGetResponseFromCall()));
    }

    private XmlPullParser fn_createParser(String strXmlString) {
        XmlPullParser parser = null;
        try {
            XmlPullParserFactory xmlParsFact = XmlPullParserFactory.newInstance();
            xmlParsFact.setNamespaceAware(true);
            parser = xmlParsFact.newPullParser();
            parser.setInput(new StringReader(strXmlString));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    public void fn_parseDiaryXMLContents(XmlPullParser parser) throws ParseException {
        try {
            TimeZone time = TimeZone.getTimeZone("UTC");
            dateFormat.setTimeZone(time);
            String text = "";
            parser.nextTag();
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case START_TAG:
                        if (tagName.equals("data")) myDiary = new diary();
                        else if (tagName.equals("item")) myAppointment = new Appointment();
                    case TEXT:
                        text = parser.getText();
                        break;
                    case END_TAG:
                        if (parser.getName().equals("strFunction")) {
                            myDiary.setStrFunction(text);
                        } else if (parser.getName().equals("intErrorId")) {
                            int errorId = Integer.parseInt(text);
                            myDiary.setIntErrorId(errorId);
                        } else if (parser.getName().equals("strError")) {
                            myDiary.setStrError(text);
                        } else if (parser.getName().equals("dtePeriodStart")) {
                            Date dateStart = dateFormat.parse(text);
                            myDiary.setDtePeriodStart(dateStart);
                        } else if (parser.getName().equals("dtePeriodEnd")) {
                            Date dateEnd = dateFormat.parse(text);
                            myDiary.setDtePeriodEnd(dateEnd);
                        } else if (parser.getName().equals("id")) {
                            int intId = Integer.parseInt(text);
                            myAppointment.setIntAppointmentid(intId);
                        } else if (parser.getName().equals("title")) {
                            myAppointment.setStrAppointmentTitle(text);
                        } else if (parser.getName().equals("start")) {
                            Date appointmentDateStart = dateFormat.parse(text);
                            myAppointment.setDteAppointmentStart(appointmentDateStart);
                        } else if (parser.getName().equals("end")) {
                            Date appointmentDateEnd = dateFormat.parse(text);
                            myAppointment.setGetDteAppointmentEnd(appointmentDateEnd);
                        } else if (parser.getName().equals("Url")) myAppointment.setStrUrl(text);
                        else if (parser.getName().equals("item"))
                            myDiary.colAppointment.add(myAppointment);
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
