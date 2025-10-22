package com.wecp.progressive.exception;

public class PatientAlreadyExistsException extends RuntimeException{
    public PatientAlreadyExistsException(String msg) {
        super(msg);
    }
}