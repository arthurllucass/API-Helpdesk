package com.arthurllucass.projeto_helpdesk.controller.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationError extends StandardError{

    private Map<String, String> errors = new HashMap<>();

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
