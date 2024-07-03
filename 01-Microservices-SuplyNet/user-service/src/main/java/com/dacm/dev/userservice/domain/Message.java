package com.dacm.dev.userservice.domain;

public class Message {

    // Common messages
    public static final String SUCCESS = "Operation was successful.";
    public static final String ERROR = "Operation could not complete.";

    // Messages for user registration
    public static final String USER_FOUND = "User found";
    public static final String USERNAME_TAKEN= "Username is already taken";
    public static final String EMAIL_TAKEN = "Email is already taken";
    public static final String PASSWORD_LENGTH= "Password must be more than 5 characters";
    public static final String PASSWORD_MANDATORY= "Password must be mandatory";
    public static final String EMAIL_MANDATORY= "Email must be mandatory";
    public static final String USERNAME_MANDATORY= "UserID must be mandatory";


    // Messages for update user
    public static final String USER_SAVE_SUCCESSFULLY= "user saved successfully";
    public static final String USER_UPDATE_SUCCESSFULLY= "user updated successfully";
    public static final String USER_DELETE_SUCCESSFULLY= "user deleted successfully";
    public static final String USER_NOT_FOUND= "user not found";
    public static final String USER_EMAIL_ALREADY_EXISTS= "user email already exists";
    public static final String USER_NAME_ALREADY_EXISTS= "user firstName already exists";

}
