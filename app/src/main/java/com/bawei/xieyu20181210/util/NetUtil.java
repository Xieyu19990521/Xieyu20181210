package com.bawei.xieyu20181210.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class NetUtil {
    public static String requestData(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);
        int responseCode = urlConnection.getResponseCode();
        if(responseCode==200){
            String string1 = streamToString(urlConnection.getInputStream());
            return string1;
        }
        return null;
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder=new StringBuilder();
        for (String tmp=bufferedReader.readLine();tmp!=null;tmp=bufferedReader.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
    public static boolean isMobile(String s){
        String ismobile="^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        return Pattern.matches(ismobile,s);
    }
}
