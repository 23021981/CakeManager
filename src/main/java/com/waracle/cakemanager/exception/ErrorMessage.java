package com.waracle.cakemanager.exception;
/*
* Author : Atul Kumar
* */
public class ErrorMessage extends Exception{
    private String errorId;

    public ErrorMessage(String errorId, String message) {
        super(message);
        this.errorId = errorId;
    }

    public String getErrorId() {
        return errorId;
    }

}
