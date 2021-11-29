package com.epam.esm.zotov.mjcschool.api.exception.advice;

import java.util.Locale;

import javax.validation.ConstraintViolationException;

import com.epam.esm.zotov.mjcschool.api.exception.RestExceptionResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@PropertySource("classpath:error.properties")
public class LocalizedGeneralExceptionAdvice {
    @Value("${error.msg.400}")
    private String error400;
    @Value("${error.code.400}")
    private String error400Code;
    @Value("${error.msg.401}")
    private String error401;
    @Value("${error.code.401}")
    private String error401Code;
    @Value("${error.msg.404}")
    private String error404;
    @Value("${error.code.404}")
    private String error404Code;
    @Value("${error.msg.500}")
    private String error500;
    @Value("${error.code.500}")
    private String error500Code;
    private MessageSource messageSource;

    @Autowired
    public LocalizedGeneralExceptionAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ TypeMismatchException.class, MissingServletRequestParameterException.class,
            IllegalArgumentException.class, HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class, MethodArgumentNotValidException.class,
            ConstraintViolationException.class })
    public ResponseEntity<RestExceptionResponse> handleBadRequest(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error400, null, locale),
                error400Code);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestExceptionResponse> handleNoHandlerFoundException(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error404, null, locale),
                error404Code);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<RestExceptionResponse> handleUnauthorized(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error401, null, locale),
                error401Code);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestExceptionResponse> handleGenericException(Locale locale) {
        RestExceptionResponse response = new RestExceptionResponse(messageSource.getMessage(error500, null, locale),
                error500Code);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}