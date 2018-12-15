package com.lyriad.flitrio.Classes;

import java.time.LocalDate;

public class User extends Person {

    private String username;
    private String password;
    private String email;
    private LocalDate registerDate;
    private LocalDate lastAccess;
    private Subscription subscription;
    private boolean admin;

    public User(String givenName, String middleName, String lastName, LocalDate birthDate,
                String countryOfOrigin, char gender, String username, String password,
                String email, LocalDate registerDate, LocalDate lastAccess){

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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(LocalDate lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
