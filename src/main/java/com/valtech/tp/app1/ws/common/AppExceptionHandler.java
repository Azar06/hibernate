package com.valtech.tp.app1.ws.common;

import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String processThrowable(Throwable exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String processEntityAlreadyExist(EntityAlreadyExist exception) {
        return exception.getMessage();
    }

}
