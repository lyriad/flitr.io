package com.lyriad.flitrio.Classes;

import java.time.LocalDate;

public class Person {

    private String givenName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String countryOfOrigin;
    private String gender;
    private String personImage;

    public Person(){
        super();
    }

    public Person(String givenName, String middleName, String lastName, LocalDate birthDate,
                  String countryOfOrigin, String gender, String personImage){
        super();
        this.givenName = givenName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.countryOfOrigin = countryOfOrigin;
        this.gender = gender;
        this.personImage = personImage;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

}
