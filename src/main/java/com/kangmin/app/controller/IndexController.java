package com.kangmin.app.controller;

import com.kangmin.app.model.CustomResponse;
import com.kangmin.app.util.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @ResponseBody
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<CustomResponse> indexInfo() {
        final CustomResponse response = new CustomResponse();
        response.setMessage(Message.INDEX);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
