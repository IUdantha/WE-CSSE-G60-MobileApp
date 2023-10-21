package com.example.csseticketingmobileapp.common;

import android.content.Context;

import com.example.csseticketingmobileapp.interfaces.Passenger;

public class SignUpFactory {
    public static Passenger createPassenger(Context context, String accountType, String fName, String lName, String email, String password, String identifier) {
        if ("local".equalsIgnoreCase(accountType)) {
            return new LocalPassenger(context, fName, lName, email, password, identifier, accountType);
        } else if ("foreign".equalsIgnoreCase(accountType)) {
            return new ForeignPassenger(context, fName, lName, email, password, identifier, accountType);
        }
        return null; // Handle unknown types or errors
    }
}

