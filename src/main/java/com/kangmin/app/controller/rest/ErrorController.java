package com.kangmin.app.controller.rest;

import com.kangmin.app.model.response.CustomResponse;
import com.kangmin.app.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/invalid-session", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> handleInvalidSession() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.NOT_LOGGED_IN);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> handleNotAuthorized() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.NOT_AUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/access-excluded", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> handleAccessExcluded() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.ACCESS_EXCLUDED);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
