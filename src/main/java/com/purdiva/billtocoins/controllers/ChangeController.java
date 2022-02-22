package com.purdiva.billtocoins.controllers;

import com.purdiva.billtocoins.request.ChangeRequest;
import com.purdiva.billtocoins.request.FillDrawerRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.response.FillDrawerResponse;
import com.purdiva.billtocoins.services.ChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/change")
public class ChangeController {

    private final ChangeService changeService;
    private static final Logger log = LoggerFactory.getLogger(ChangeController.class);

    public ChangeController(ChangeService changeService) {
        this.changeService = changeService;
    }

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

    @GetMapping("/drawer")
    public FillDrawerResponse getDrawer() {
        log.debug("Retrieve the current coin counts of the cash drawer");
        return changeService.getDrawerCounts();
    }

}
