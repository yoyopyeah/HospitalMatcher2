package com.example.hospitalmatcher;

import java.util.ArrayList;
import java.util.HashMap;

public class HospitalDataBase {

    private ArrayList<Hospital> allHospitals;
    private ArrayList<Hospital> allValidHospitals;
    // current location of the user
    private Double currentX;
    private Double currentY;


    // Constructor
    public HospitalDataBase(Double x, Double y) {

        this.allHospitals = new ArrayList<Hospital>();
        this.allValidHospitals = new ArrayList<Hospital>();
        this.currentX = x;
        this.currentY = y;

        // create manually 5 Montreal hospitals
        allHospitals.add(new Hospital("Montreal General Hospital", true, 45.49752494338774, -73.58853601085194, 0));
        allHospitals.add(new Hospital("Jewish General Hospital", true, 45.49696267784886, -73.63021013836273, 0));
        allHospitals.add(new Hospital("St. Mary's Hospital", true, 45.4951352243607, -73.62383807250717, 0));
        allHospitals.add(new Hospital("CHUM", true, 45.51309376029247, -73.5576694104104, 0));
        allHospitals.add(new Hospital("Catherine Booth Hospital", true, 45.465080168596934, -73.6361520918609, 0));

    }

    public ArrayList<Hospital> getAllHospitals(){
        return allHospitals;
    }


    public void sortHospitals() {
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
            while (k > 0 && tmpHospital.getMinutesToArrive() < allValidHospitals.get(k - 1).getMinutesToArrive()) {
                allValidHospitals.set(k, allValidHospitals.get(k - 1));
                k--;
            }
            allValidHospitals.set(k, tmpHospital);
        }
    }

    // return the array list that contains the names of hospitals in order of how
    // long it would take to get to the hospital
    public ArrayList<String> getHospitalsNames() {
        ArrayList<String> arrayString = new ArrayList<String>();

        for (Hospital h : allValidHospitals) {
            arrayString.add(h.getName());
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
        private Double x;
        private Double y;
        private int time; // how far away is the hospital in minutes

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


        public int getMinutesToArrive() {
            return time;
        }

        public void setMinutesToArrive(int minutesToArrive) {
            this.time = minutesToArrive;
        }

        // setters and getters end
    }

}