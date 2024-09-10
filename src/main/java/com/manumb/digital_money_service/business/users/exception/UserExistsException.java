package com.manumb.digital_money_service.business.users.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message){
        super(message);
    }
}
