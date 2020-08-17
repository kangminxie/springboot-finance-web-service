package com.kangmin.app.controller.rest;

import com.kangmin.app.model.CustomResponse;
import com.kangmin.app.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class TestController {

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> checkOkRequest() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/bad", method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> checkBadRequest() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
