package com.kangmin.app.util;

public final class Message {

    // == account related ==
    public static final String NOT_LOGGED_IN = "You are not currently logged in";
    public static final String INCORRECT_CREDENTIALS = "There seems to be an issue with the username/password combination that you entered";
    public static final String LOGGED_OUT = "You have been successfully logged out";
    public static final String CURRENT_PASSWORD_INCORRECT = "The current password is incorrect";
    public static final String PROFILE_UPDATE_SUCCESS = "Your profile has been updated successfully";
    public static final String PROFILE_UPDATE_FAILED = "Sorry, your profile update is not processed, please retry";
    public static final String PASSWORD_UPDATE_SUCCESS = "Your password has been updated successfully";

    // == funds related ==
    public static final String INSUFFICIENT_CASH_BUY_FUND = "You don't have enough cash in your account to make this purchase";
    public static final String FUND_DOES_NOT_EXIST = "The fund you provided does not exist";
    public static final String BUY_FUND_SUCCESS = "The fund has been successfully purchased";
    public static final String SELL_FUND_SUCCESS = "The shares have been successfully sold";
    public static final String INSUFFICIENT_SHARE = "You don’t have that many shares in your portfolio";
    public static final String NO_FUNDS_IN_PORTFOLIO = "You don't have any funds in your Portfolio";

    // == check deposit & request ==
    public static final String DEPOSIT_CHECK_SUCCESS = "The check was successfully deposited";
    public static final String REQUEST_CHECK_SUCCESS = "Your check request has been initialized";
    public static final String INSUFFICIENT_CASH_CHECK_REQUEST = "You don't have sufficient funds in your account to cover the requested check";

    // == admin ==
    public static final String ACCOUNT_PASSWORD_NOT_MATCH = "The password does not match";
    public static final String ACCOUNT_ALREADY_EXIST = "The account with input email or username is already existed";
    public static final String ACCOUNT_DOES_NOT_EXIST = "The account associated with request doesn't exist";
    public static final String ACCOUNT_CREATED_SUCCESS = "The account was successfully created";
    public static final String FUND_ALREADY_EXIST = "The fund with name or symbol is already existed";
    public static final String FUND_CREATED_SUCCESS = "The fund was successfully created";

    // == privilege handle ==
    public static final String NOT_AUTHORIZED = "You are not authorized to access it";
    public static final String ACCESS_EXCLUDED = "Your privilege does not fit for this action";
    public static final String TRANSITION_DAY_SUCCESS = "The fund prices have been successfully recalculated";

    // == test or general ==
    public static final String INDEX = "Hello user, please sign-in to perform actions";
    public static final String UNSUPPORTED = "unsupported request";
    public static final String REQUEST_FIELD_ISSUE = "please check your request fields";
}
