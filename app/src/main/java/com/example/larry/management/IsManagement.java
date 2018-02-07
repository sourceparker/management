package com.example.larry.management;

/**
 * Created by Larry on 2/5/2018.
 */

public class IsManagement {
    private String user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String phoneNumber;

    public IsManagement() {
    }

    public IsManagement(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber=phoneNumber;
    }

    public IsManagement(String user_id,String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.user_id=user_id;
        this.phoneNumber = phoneNumber;


    }

    public String getUser_id() {return user_id;}

    public void setUser_id(String user_id) {this.user_id = user_id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName +
                ", lastName='" + lastName +
                ", email='" + email +
                ", password='" + password +
                ", phoneNumber='" + phoneNumber;

    }
}
