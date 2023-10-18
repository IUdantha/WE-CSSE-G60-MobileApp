package com.example.csseticketingmobileapp.models;

public class Passenger {
    private String passengerId;
    private String fName;
    private String lName;
    private String email;
    private String password;

    // Constructors

    // Default constructor
    public Passenger() {
        // No-argument constructor
    }

    // Parameterized constructor
    public Passenger(String passengerId, String fName, String lName, String email, String password) {
        this.passengerId = passengerId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    // Getter and Setter methods for class fields

    // Getter for passengerId
    public String getPassengerId() {
        return passengerId;
    }

    // Setter for passengerId
    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    // Getter for fName
    public String getfName() {
        return fName;
    }

    // Setter for fName
    public void setfName(String fName) {
        this.fName = fName;
    }

    // Getter for lName
    public String getlName() {
        return lName;
    }

    // Setter for lName
    public void setlName(String lName) {
        this.lName = lName;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Override the toString() method for better class representation
    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId='" + passengerId + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
