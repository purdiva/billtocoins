package com.purdiva.billtocoins.controllers;

import com.purdiva.billtocoins.request.ChangeRequest;
import com.purdiva.billtocoins.request.FillDrawerRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.response.FillDrawerResponse;
import com.purdiva.billtocoins.services.ChangeService;
import com.purdiva.billtocoins.services.ChangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/change")
public class ChangeController {

    @Autowired
    private ChangeService changeService;

    private static final Logger log = LoggerFactory.getLogger(ChangeController.class);


    @PostMapping("/makechange")
    public ChangeResponse makeChange(@RequestBody ChangeRequest changeRequest) {
        log.debug("MakeChange for ${} bill", changeRequest.getBillDenomination());
        return changeService.makeChange(changeRequest.getBillDenomination());
    }

    @PostMapping("/filldrawer")
    public FillDrawerResponse fillDrawer(@RequestBody FillDrawerRequest fillDrawerRequest) {
        log.debug("Update the drawer with this amount of coins: " + fillDrawerRequest.toString());
        changeService.fillDrawer(fillDrawerRequest);
        return changeService.getDrawerCounts();
    }

}
