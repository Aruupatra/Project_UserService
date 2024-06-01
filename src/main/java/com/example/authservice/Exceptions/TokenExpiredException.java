package com.example.authservice.Exceptions;

public class TokenExpiredException extends Exception{

    public TokenExpiredException(String message)
    {
        super(message);
    }
}
