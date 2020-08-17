package com.kangmin.app.util;

public final class Message {

    // == account related ==
    public static final String NOT_LOGGED_IN = "You are not currently logged in";
    public static final String INCORRECT_CREDENTIALS = "There seems to be an issue with the username/password combination that you entered";
    public static final String LOGGED_OUT = "You have been successfully logged out";

    // == privilege handle ==
    public static final String NOT_AUTHORIZED = "You are not authorized to access it";

    // == admin ==
    public static final String ACCOUNT_PASSWORD_NOT_MATCH = "The password does not match";
    public static final String ACCOUNT_ALREADY_EXIST = "The account with input email or username is already existed";
    public static final String ACCOUNT_CREATED_SUCCESS = "The account was successfully created";
    public static final String FUND_ALREADY_EXIST = "The fund with name or symbol is already existed";
    public static final String FUND_CREATED_SUCCESS = "The fund was successfully created";

    // == general ==
    public static final String INDEX = "Hello user, please sign-in to perform actions";
    public static final String OK = "status is good";
    public static final String BAD_REQUEST = "bad request";
}
