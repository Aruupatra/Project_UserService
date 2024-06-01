package com.example.authservice.Exceptions;

public class UserAlreadyExist extends Exception{

    public UserAlreadyExist(String message)
    {
        super(message);
    }
}
