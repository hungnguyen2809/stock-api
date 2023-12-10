package com.hungnv28.core.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorResponse> handle(ApiException exception) {
        ErrorResponse response = new ErrorResponse(exception);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleCatchAll(Exception exception) {
        logger.error("SERVER_ERROR: {}", exception.getMessage(), exception);

        ErrorResponse response = new ErrorResponse();
        response.setError(true);

        if (exception instanceof HttpRequestMethodNotSupportedException || exception instanceof MissingServletRequestParameterException) {
            String message;
            if (exception.getMessage() != null && exception.getMessage().contains("Required")) {
                message = "Thiếu tham sô. Vui lòng kiểm tra lại";
            } else {
                message = "Sai phương thức. Vui lòng kiểm tra lại";
            }

            response.setCode(CodeException.SERVER_ERROR.getValue());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(message);
        } else if (exception instanceof HttpMediaTypeNotSupportedException || exception instanceof HttpMessageNotReadableException || exception instanceof InvalidFormatException) {
            response.setCode(CodeException.SERVER_ERROR.getValue());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Sai định dạng dữ liệu");
        } else if (exception instanceof MethodArgumentNotValidException) {
            StringBuilder message = new StringBuilder();
            BindingResult bindingresult = ((MethodArgumentNotValidException) exception).getBindingResult();
            List<FieldError> fieldErrors = bindingresult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(";");
            }

            response.setCode(CodeException.SERVER_ERROR.getValue());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(message.toString());
        } else {
            response.setCode(CodeException.SERVER_ERROR.getValue());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Đã có lỗi xảy ra. Vui lòng thử lại sau.");
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
