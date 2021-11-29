package com.epam.esm.zotov.mjcschool.api.exception.advice;

import java.util.Locale;

import com.epam.esm.zotov.mjcschool.api.exception.AuthorizationFailedException;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.api.exception.RequestNotExecutedException;
import com.epam.esm.zotov.mjcschool.api.exception.RestExceptionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@PropertySource("classpath:error.properties")
public class LocalizedCustomExceptionAdvice {
    @Value("${error.msg.4010}")
    private String error4010;
    @Value("${error.code.4010}")
    private String error4010Code;
    @Value("${error.msg.4040}")
    private String error4040;
    @Value("${error.code.4040}")
    private String error4040Code;
    @Value("${error.msg.5001}")
    private String error5001;
    @Value("${error.code.5001}")
    private String error5001Code;
    private MessageSource messageSource;

    @Autowired
    public LocalizedCustomExceptionAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestExceptionResponse> handleNoResourceFound(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error4040, null, locale),
                error4040Code);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestNotExecutedException.class)
    public ResponseEntity<RestExceptionResponse> handleRequestNotExectued(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error5001, null, locale),
                error5001Code);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<RestExceptionResponse> handleUnauthorizedException(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error4010, null, locale),
                error4010Code);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}