package com.example.hospitalmatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HospitalDataBase {

    private ArrayList<Hospital> allHospitals;
    private ArrayList<Hospital> allValidHospitals;
    // current location of the user
    private Double currentX;
    private Double currentY;


    // Constructor
    public HospitalDataBase(Double x, Double y) throws JSONException {

        this.allHospitals = new ArrayList<Hospital>();
        this.allValidHospitals = new ArrayList<Hospital>();
        this.currentX = x; // user Latitude
        this.currentY = y; // user Longitude

        // create manually 5 Montreal hospitals
        allHospitals.add(new Hospital("Montreal General Hospital", true, 45.49752494338774, -73.58853601085194, 0));
        allHospitals.add(new Hospital("Jewish General Hospital", true, 45.49696267784886, -73.63021013836273, 0));
        allHospitals.add(new Hospital("St. Mary's Hospital", true, 45.4951352243607, -73.62383807250717, 0));
        allHospitals.add(new Hospital("CHUM", true, 45.51309376029247, -73.5576694104104, 0));
        allHospitals.add(new Hospital("Catherine Booth Hospital", true, 45.465080168596934, -73.6361520918609, 0));

        //changes made here, we need to add all the valid hospital to the allValidHospitals field
        sortHospitals();
    }



    public ArrayList<Hospital> getAllHospitals(){
        return allHospitals;
    }


    // sort hospitals by availability and time
    public void sortHospitals() throws JSONException {

        // if the hospital is available, then add it to the list of Valid Hospitals
        for (Hospital h : allHospitals) {
            if (h.isHasSpots()) {
                allValidHospitals.add(h);
            }
        }

        // sort the valid hospitals according to their times
        // insertion sort
        for (int i = 0; i < allValidHospitals.size(); i++) {
            Hospital tmpHospital = allValidHospitals.get(i);
            int k = i;
            //todo: update getMinutesToArrive by using Google Map API
            while (k > 0 && tmpHospital.getMinutesToArrive() < allValidHospitals.get(k - 1).getMinutesToArrive()) {
                allValidHospitals.set(k, allValidHospitals.get(k - 1));
                k--;
            }
            allValidHospitals.set(k, tmpHospital);
        }
    }


    // return the array list that contains the names of hospitals in order of how
    // long it would take to get to the hospital
    //todo: getHospitalNames to show on the app
    public ArrayList<String> getHospitalsNames() {
        ArrayList<String> arrayString = new ArrayList<String>();

        for (Hospital h : allValidHospitals) {
            arrayString.add(h.getName());
            System.out.println(h.getName());
        }
        return arrayString;
    }


    // return allValidHospitals
    public ArrayList<Hospital> getAllValidHospitals() {
        return allValidHospitals;
    }

    private class Hospital {
        private String name;
        private boolean hasSpots;
        private Double x; //latitude
        private Double y; //longitude
        private int time; // how far away is the hospital in minutes
        private String timeText; // verbose time

        public Hospital(String name, boolean hasSpots, Double x, Double y, int time) {
            this.name = name;
            this.hasSpots = hasSpots;
            this.x = x;
            this.y =y;

            this.time = time;
        }

        // setters and getters start
        public String getName() {
            return name;
        }

        public boolean isHasSpots() {
            return hasSpots;
        }

        public void setHasSpots(boolean hasSpots) {
            this.hasSpots = hasSpots;
        }


        /*
        Google Map API return multiple routes. Due to time limits, we are only getting the first route
         */
        //todo: updateMinutesToArrive
        public void updateMinutesToArrive() throws JSONException {

            String url = this.generateUrl();
            JSONObject json = new JSONObject(url);
            /*** test ***/
            System.out.println("url is: " + url);

            // just gonna get the first route
            JSONObject duration = json.getJSONArray("routes").getJSONObject(0).getJSONObject("duration");

            this.time = duration.getInt("value");
            this.timeText = duration.getString("text");
            /*** test ***/
            System.out.println("travel time is : " + this.timeText);
        }

        public int getMinutesToArrive() throws JSONException {
            this.updateMinutesToArrive();
            return time;
        }

        // setters and getters end


        /******** FOR API ***********/
        // generate the url to call API
        public String generateUrl() {
            String baseUrl="https://maps.googleapis.com/maps/api/directions/json?";
            String APIKey="AIzaSyCwgrS6lWwgjZlIpb3-LRsdY6A9edZ3ws0";

            String origin = "origin=" + Double.toString(currentY) + "," + Double.toString(currentX);
            String destination = "destination=" + Double.toString(this.y) + "," + Double.toString(this.x);

            String url = baseUrl + origin + "&" + destination + "&key=" + APIKey;

            return url;
        }
    }

}