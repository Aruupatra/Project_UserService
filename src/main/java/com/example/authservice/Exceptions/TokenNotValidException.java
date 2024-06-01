package com.example.authservice.Exceptions;

public class TokenNotValidException extends Exception{

    public TokenNotValidException(String message)
    {
        super(message);
    }
}
