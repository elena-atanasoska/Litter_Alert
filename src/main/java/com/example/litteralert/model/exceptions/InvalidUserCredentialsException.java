package com.example.litteralert.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(){
        super(String.format("Invalid user credentials"));
    }
}
