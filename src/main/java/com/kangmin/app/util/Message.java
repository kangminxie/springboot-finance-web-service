package com.kangmin.app.util;

public final class Message {

    // == account related ==
    public static final String NOT_LOGGED_IN = "You are not currently logged in";
    public static final String INCORRECT_CREDENTIALS = "There seems to be an issue with the username/password combination that you entered";
    public static final String LOGGED_OUT = "You have been successfully logged out";

    // == funds related ==
    public static final String INSUFFICIENT_CASH_BUY_FUND = "You don't have enough cash in your account to make this purchase";
    public static final String FUND_DOES_NOT_EXIST = "The fund you provided does not exist";
    public static final String BUY_FUND_SUCCESS = "The fund has been successfully purchased";

    public static final String INSUFFICIENT_SHARE = "You don’t have that many shares in your portfolio";

    // == check deposit & withdraw ==
    public static final String DEPOSIT_CHECK_SUCCESS = "The check was successfully deposited";

    // == privilege handle ==
    public static final String NOT_AUTHORIZED = "You are not authorized to access it";

    // == admin ==
    public static final String ACCOUNT_PASSWORD_NOT_MATCH = "The password does not match";
    public static final String ACCOUNT_ALREADY_EXIST = "The account with input email or username is already existed";
    public static final String ACCOUNT_DOES_NOT_EXIST = "The account with input email/username doesn't exist";
    public static final String ACCOUNT_CREATED_SUCCESS = "The account was successfully created";
    public static final String FUND_ALREADY_EXIST = "The fund with name or symbol is already existed";
    public static final String FUND_CREATED_SUCCESS = "The fund was successfully created";

    // == general ==
    public static final String INDEX = "Hello user, please sign-in to perform actions";
    public static final String OK = "status is good";
    public static final String BAD_REQUEST = "bad request";
}
