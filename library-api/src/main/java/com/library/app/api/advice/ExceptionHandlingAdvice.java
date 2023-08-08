package com.library.app.api.advice;

import com.library.app.services.exception.ExceptionCode;
import com.library.app.services.exception.ServiceException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected @NonNull ResponseEntity<Object> handleExceptionInternal(@NonNull final Exception ex,
                                                                      final Object body,
                                                                      @NonNull final HttpHeaders headers,
                                                                      @NonNull final HttpStatusCode status,
                                                                      @NonNull final WebRequest request) {

        final ExceptionResponse errorResponse = new ExceptionResponse(ExceptionCode.BAD_REQUEST);

        return new ResponseEntity<>(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAllUncaughtException(final Exception ex) {

        return new ExceptionResponse(ExceptionCode.BAD_REQUEST);
    }

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                           @NonNull final HttpHeaders headers,
                                                                           @NonNull final HttpStatusCode status,
                                                                           @NonNull final WebRequest request) {

        final List<String> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .toList();

        final ExceptionResponse errorResponse = new ExceptionResponse(ExceptionCode.UNPROCESSABLE_ENTITY, fields);

        return new ResponseEntity<>(errorResponse, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleTransactionException(final TransactionSystemException ex) {

        final ExceptionResponse errorResponse = new ExceptionResponse(ExceptionCode.TRANSACTION_ERROR);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleServiceException(final ServiceException ex) {


        final ExceptionResponse errorResponse = new ExceptionResponse(ex.getCode());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
