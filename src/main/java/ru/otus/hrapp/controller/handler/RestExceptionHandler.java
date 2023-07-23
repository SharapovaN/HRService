package ru.otus.hrapp.controller.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackages = "ru.otus.hrapp.controller")
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleBadRequestException(MethodArgumentNotValidException ex) {
        var errors = new ArrayList<String>();
        var details = ex.getDetailMessageArguments();
        assert details != null;
        for (Object obj : details) {
            ArrayList<String> list = (ArrayList<String>) obj;
            if (list.size() > 0) {
                errors.addAll(list);
            }
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}


