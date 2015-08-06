package com.allnone.app.Controller;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by testing on 06/08/15.
 */
public class HTTPClientClass {

    private boolean boolCheck=false;

    public boolean BXP_Mobile_Post(String function, String params){
        HttpURLConnection myHttpclient = null;
        try {
            URL url = new URL(function);
            ByteBuffer databuffer = Charset.forName("UTF-8").encode(params);
            byte[] postData = databuffer.array();
            int dataLength = postData.length;
            myHttpclient = (HttpURLConnection) url.openConnection();

            myHttpclient.setDoOutput(true);
            myHttpclient.setInstanceFollowRedirects(false);
            myHttpclient.setRequestMethod("POST");
            myHttpclient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            myHttpclient.setRequestProperty("charset", "utf-8");
            myHttpclient.setRequestProperty("Content-Length", Integer.toString(dataLength));
            myHttpclient.setUseCaches(false);
            myHttpclient.getOutputStream().write(postData);
            myHttpclient.getInputStream().read();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            myHttpclient.disconnect();
        }

        return boolCheck;
    }

    public boolean BXP_Mobile_Get(String function){

        try {
            URL url = new URL(function);
            HttpURLConnection myHttpclient;
            myHttpclient = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boolCheck;
    }
}
