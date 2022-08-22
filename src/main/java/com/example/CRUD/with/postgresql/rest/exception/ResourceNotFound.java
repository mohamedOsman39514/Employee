package com.example.CRUD.with.postgresql.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends Exception{

    private static final long serialVersionID = 1L;

    public ResourceNotFound(String message){
        super(message);
    }

}
