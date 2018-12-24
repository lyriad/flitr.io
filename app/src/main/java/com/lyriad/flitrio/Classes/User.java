package com.lyriad.flitrio.Classes;

import java.util.Date;

public class User extends Person {

    private String username;
    private String password;
    private String email;
    private Date registerDate;
    private Date lastAccess;
    private String subscription;
    private boolean admin;

    public User(){
        super();
    }

    public User(String givenName, String middleName, String lastName, String birthDate,
                String countryOfOrigin, String gender, String username, String password,
                String email, Date registerDate, Date lastAccess){

        super(givenName, middleName, lastName, birthDate, countryOfOrigin, gender);
        this.username = username;
        this.password = password;
        this.email = email;
        this.registerDate = registerDate;
        this.lastAccess = lastAccess;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
