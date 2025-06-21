package com.estepnv.hotel_advisor.iam;

import com.estepnv.hotel_advisor.exceptions.ApplicationException;

public class UserPasswordDoesNotMatchException extends ApplicationException {
    public UserPasswordDoesNotMatchException() {
        super("User password does not match");
    }
}
