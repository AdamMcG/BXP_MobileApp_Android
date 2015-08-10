package com.allnone.app;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class HttpRequest {
    private StringBuilder strResponseFromCall = new StringBuilder();

    public String fnStrGetResponseFromCall() {
        return strResponseFromCall.toString();
    }

    public void fnStrSetResponseFromCall(StringBuilder strResponse) {
        strResponseFromCall = strResponse;
    }

    public String fnStrSettingParameters(List<NameValuePair> Params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair strItem : Params) {
            if (first)
                first = false;
            else
                result.append("&");
            try {
                result.append(URLEncoder.encode(strItem.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(strItem.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public void fn_BXP_PostCall(String strFunction, List<NameValuePair> KVParams) {
        HttpURLConnection httpClient = null;
        try {
            String strParameters = fnStrSettingParameters(KVParams);
            URL url = new URL(strFunction);
            httpClient = (HttpURLConnection) url.openConnection();
            httpClient.setDoOutput(true);
            httpClient.setDoInput(true);
            httpClient.setChunkedStreamingMode(0);
            OutputStream out = httpClient.getOutputStream();
            BufferedWriter ioWriteToRequest = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            ioWriteToRequest.write(strParameters);
            int intResponseCode = httpClient.getResponseCode();

            if (intResponseCode == HttpURLConnection.HTTP_OK) {
                InputStream ioResponseStream = httpClient.getInputStream();
                InputStreamReader ioStreamReader = new InputStreamReader(ioResponseStream);
                BufferedReader bIOReader = new BufferedReader(ioStreamReader);
                String strReaderLine;
                while ((strReaderLine = bIOReader.readLine()) != null) {
                    strResponseFromCall.append(strReaderLine);

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpClient != null;
            httpClient.disconnect();
        }
    }

    public void fn_BXP_GetCall(String function) {
        HttpURLConnection httpClient = null;
        try {
            URL url = new URL(function);
            httpClient = (HttpURLConnection) url.openConnection();
            httpClient.setDoInput(true);

            int intResponseCode = httpClient.getResponseCode();
            if (intResponseCode == HttpURLConnection.HTTP_OK) {
                InputStream ioResponseStream = httpClient.getInputStream();
                InputStreamReader ioStreamReader = new InputStreamReader(ioResponseStream);
                BufferedReader bIOReader = new BufferedReader(ioStreamReader);
                String StrReaderLine;
                while ((StrReaderLine = bIOReader.readLine()) != null) {
                    strResponseFromCall.append(StrReaderLine);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpClient != null;
            httpClient.disconnect();
        }

    }
}
