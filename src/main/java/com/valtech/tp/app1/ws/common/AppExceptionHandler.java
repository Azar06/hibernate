package com.valtech.tp.app1.ws.common;

import com.valtech.tp.app1.domain.model.commun.EntityAlreadyExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class AppExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String processThrowable(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        return throwable.getMessage();
    }

    @ExceptionHandler(EntityAlreadyExist.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String processEntityAlreadyExist(EntityAlreadyExist exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(
            {
                    MethodArgumentNotValidException.class,
                    IllegalArgumentException.class
            })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String processNotValid(Throwable throwable) {
        return throwable.getMessage();
    }


}
