package com.lyriad.flitrio.Classes;

import java.time.LocalDate;
import java.util.List;

public class User extends Person {

    private String username;
    private String email;
    private String subscription;
    private List<String> list;

    public User(){
        super();
    }

    public User(String givenName, String middleName, String lastName, LocalDate birthDate,
                String countryOfOrigin, String gender, String username, String email,
                String subscription, List<String> list){

        super(givenName, middleName, lastName, birthDate, countryOfOrigin, gender, null);
        this.username = username;
        this.email = email;
        this.subscription = subscription;
        this.list = list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
