package com.sandeep.indplus.Model;

public class User {
    private String  phone, email, state, city;

    public User() {
    }

    public User(String phone, String email, String state, String city) {
        this.phone = phone;
        this.email = email;
        this.state = state;
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
