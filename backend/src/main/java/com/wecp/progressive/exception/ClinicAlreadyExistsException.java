package com.wecp.progressive.exception;

public class ClinicAlreadyExistsException extends RuntimeException{
    public ClinicAlreadyExistsException(String msg) {
        super(msg);
    }
}