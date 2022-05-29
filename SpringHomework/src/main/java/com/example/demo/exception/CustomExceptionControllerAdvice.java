package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionControllerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MyCustomException handleGenericException(Exception e){
        return new MyCustomException(e.getMessage(),e.getClass().getName());
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MyCustomException handleNoSuchElementException(Exception e){
        return new MyCustomException(e.getMessage(), e.getClass().getName());
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MyCustomException handleMessageNotReadableException(HttpMessageNotReadableException e){
        return new MyCustomException(e.getMessage(),e.getClass().getName());
    }

    @ExceptionHandler({HttpClientErrorException.BadRequest.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MyCustomException handleBadRequestException(HttpClientErrorException.BadRequest e){
        return new MyCustomException(e.getMessage(),e.getClass().getName());
    }

    @ExceptionHandler({NotAuthorizedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MyCustomException handleNotAuthorizedException(NotAuthorizedException e){
        return new MyCustomException(e.getMessage(), e.getClass().getName());
    }
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MyCustomException handleEntityNotFoundException(EntityNotFoundException e){
        return new MyCustomException(e.getMessage(), e.getClass().getName());
    }


}
