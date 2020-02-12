package com.jumkid.oauthcentral.controller;

import com.jumkid.oauthcentral.controller.response.CustomErrorResponse;
import com.jumkid.oauthcentral.exception.DataMutationException;
import com.jumkid.oauthcentral.exception.DataNotFoundException;
import com.jumkid.oauthcentral.exception.DuplicateValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceController {

    @ExceptionHandler(DataMutationException.class)
    @ResponseStatus(NOT_MODIFIED)
    public CustomErrorResponse handleDataMutationException(DataMutationException ex) {
        return buildResponse("Failed to mutate data.", ex);
    }

    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(CONFLICT)
    public CustomErrorResponse handleDuplicateValueException(DuplicateValueException ex) {
        return buildResponse("Duplicate data value found.", ex);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public CustomErrorResponse handleDataNotFoundException(DataNotFoundException ex) {
        return buildResponse("Data value is not found.", ex);
    }

    private CustomErrorResponse buildResponse(String logMessage, Exception ex) {
        log.info(logMessage, ex);
        return new CustomErrorResponse(Calendar.getInstance().getTime(), ex.getMessage());
    }

}
