package com.purdiva.billtocoins.controllers;

import com.purdiva.billtocoins.request.ChangeRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.services.ChangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/change")
public class ChangeController {

    @Autowired
    private ChangeServiceImpl changeServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(ChangeController.class);


    @PostMapping("/makechange")
    public @ResponseBody ChangeResponse makeChange(@RequestBody ChangeRequest changeRequest) {
        ChangeResponse resp = changeServiceImpl.makeChange(changeRequest.getBillDenomination());
        return resp;
    }

}
