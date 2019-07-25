package com.sandeep.indplus;

import java.util.List;

public class State {
    private String stateName;
    private List<String> cities;

    public State(String stateName, List<String> cities) {
        this.stateName = stateName;
        this.cities = cities;
    }

    public String getStateName() {
        return stateName;
    }

    public List<String> getCities() {
        return cities;
    }
//    classpath 'com.google.gms:google-services:4.2.0'
//    implementation 'com.google.firebase:firebase-core:17.0.0'

}
