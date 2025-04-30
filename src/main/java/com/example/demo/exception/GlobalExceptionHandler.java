package com.example.demo.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Custom error attributes
    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    // Handle exceptions for REST API
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle exceptions for Thymeleaf views
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex, WebRequest webRequest) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Get standard error attributes
        Map<String, Object> errorAttributesMap = this.errorAttributes.getErrorAttributes(
                webRequest, 
                org.springframework.boot.web.error.ErrorAttributeOptions.defaults());
        
        // Add additional attributes
        errorAttributesMap.put("url", request.getRequestURL().toString());
        errorAttributesMap.put("error", ex.getMessage() != null ? ex.getMessage() : "알 수 없는 오류가 발생했습니다.");
        
        modelAndView.addAllObjects(errorAttributesMap);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    // Error response class for REST API
    public static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
} 