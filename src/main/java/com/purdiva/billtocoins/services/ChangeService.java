package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.response.ChangeResponse;

public interface ChangeService {

    ChangeResponse makeChange(Integer bill);
    void resetCashDrawer();

}
