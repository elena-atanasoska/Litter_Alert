package com.example.litteralert.model.exceptions;

public class InvalidArgumentsException extends RuntimeException{

    public InvalidArgumentsException(){
        super(String.format("Invalid arguments"));
    }
}
