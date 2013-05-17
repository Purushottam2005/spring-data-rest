package org.emaginalabs.example.springdata.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * User: jaluque
 * Date: 16/05/13
 * Time: 16:18
 */
public class FormValidationError extends Exception {

    private List<FieldError> fieldErrors;

    public FormValidationError(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}