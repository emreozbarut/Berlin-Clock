package com.protel.berlin.clock.api.exception.handler;

import com.protel.berlin.clock.api.enums.Constants;
import com.protel.berlin.clock.api.exception.model.InvalidTimeException;
import com.protel.berlin.clock.api.exception.model.NumericTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BerlinClockResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    protected String handleIllegalArgumentException(IllegalArgumentException exception) {
        return String.format(Constants.EXCEPTION_MESSAGE.getPattern(), exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            NumericTimeException.class, InvalidTimeException.class
    })
    protected String handleCustomExceptions(RuntimeException exception) {
        return String.format(Constants.EXCEPTION_MESSAGE.getPattern(), exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    protected String handleException(Exception exception) {
        return String.format(Constants.EXCEPTION_MESSAGE.getPattern(), exception.getMessage());
    }
}
