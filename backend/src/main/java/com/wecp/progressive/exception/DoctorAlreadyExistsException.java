package com.wecp.progressive.exception;

public class DoctorAlreadyExistsException extends RuntimeException{
    public DoctorAlreadyExistsException(String msg) {
        super(msg);
    }
}