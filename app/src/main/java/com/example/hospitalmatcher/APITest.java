package com.example.hospitalmatcher;

import android.util.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class APITest {
    private double userLongitude = 0;
    private double userLatitude = 0;
    private double HLong = 0;
    private double HLat = 0;

    public static void main(String[] args) throws IOException, JSONException {
//        JSONObject json = readJsonFromUrl(generateUrl(0, 0, 0, 0));
//        System.out.println(json.toString());

        JSONObject obj = new JSONObject(generateUrl(0, 0, 0, 0));
        System.out.println(obj.getString("status"));
    }

    // generate the url to call API
    public static String generateUrl(double userLongitude, double userLatitude, double HLong, double HLat) {
        String baseUrl="https://maps.googleapis.com/maps/api/directions/json?";
        String APIKey="AIzaSyCwgrS6lWwgjZlIpb3-LRsdY6A9edZ3ws0";

        String origin = "origin=" + Double.toString(userLongitude) + "," + Double.toString(userLatitude);
        String destination = "destination=" + Double.toString(HLong) + "," + Double.toString(HLat);

        String url = baseUrl + origin + "&" + destination + "&key=" + APIKey;

        return url;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


}