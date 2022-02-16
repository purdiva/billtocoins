package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.request.FillDrawerRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.response.FillDrawerResponse;

public interface ChangeService {

    ChangeResponse makeChange(Integer bill);
    void fillDrawer(FillDrawerRequest fillDrawerRequest);
    FillDrawerResponse getDrawerCounts();

}
