package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.exception.InvalidBillDenominationException;
import com.purdiva.billtocoins.exception.NotEnoughChangeException;
import com.purdiva.billtocoins.request.FillDrawerRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.response.FillDrawerResponse;
import com.purdiva.billtocoins.util.CashDrawerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.purdiva.billtocoins.util.CashDrawerUtils.FORTY_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.FIFTY_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.FIVE_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.HUNDRED_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.ONE_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.TEN_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.TWENTY_DOLLAR_BILL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.TWO_DOLLAR_BILL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ChangeServiceImplTest {

    @Autowired
    private ChangeServiceImpl changeService;

    @Autowired
    CashDrawerUtils.CashDrawer cashDrawer;

    @BeforeEach
    void initCashDrawer() {
        cashDrawer.resetCashDrawer();
    }

    @Test
    void makeChange_Exceed_Drawer_Value_Hundred_Ten_dollars_throws_NotEnoughChangeException() {
        ChangeResponse changeResponse100 = changeService.makeChange(HUNDRED_DOLLAR_BILL);
        assertEquals(100, changeResponse100.getThirtyfourCount());
        assertEquals(100, changeResponse100.getThirtythreeCount());
        assertEquals(100, changeResponse100.getQuarterCount());
        assertEquals(80, changeResponse100.getDimeCount());
        assertEquals(0, changeResponse100.getNickelCount());
        assertEquals(0, changeResponse100.getPennyCount());

        Exception exception = assertThrows(NotEnoughChangeException.class, () -> changeService.makeChange(TEN_DOLLAR_BILL));
        final String expectedMessage = "Not enough money to change a $10 bill";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void makeChange_Forty_dollars_Invalid_Denomination_Exception() {        //$40 to see unacceptable denomination error
        Exception exception = assertThrows(InvalidBillDenominationException.class, () -> changeService.makeChange(FORTY_DOLLAR_BILL));
        final String expectedMessage = "$40 bills are not accepted at this machine.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void makeChange_Negative_dollars_Invalid_Denomination_Exception() {        //-$1 to see unacceptable denomination error
        Exception exception = assertThrows(InvalidBillDenominationException.class, () -> changeService.makeChange(-1));
        final String expectedMessage = "$-1 bills are not accepted at this machine.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void makeChange_Hundred_dollars_success() {
        ChangeResponse changeResponse100 = changeService.makeChange(HUNDRED_DOLLAR_BILL);
        assertEquals(100, changeResponse100.getThirtyfourCount());
        assertEquals(100, changeResponse100.getThirtythreeCount());
        assertEquals(100, changeResponse100.getQuarterCount());
        assertEquals(80, changeResponse100.getDimeCount());
        assertEquals(0, changeResponse100.getNickelCount());
        assertEquals(0, changeResponse100.getPennyCount());
    }

    @Test
    void makeChange_Fifty_dollars_success() {
        ChangeResponse changeResponse50 = changeService.makeChange(FIFTY_DOLLAR_BILL);
        assertEquals(100, changeResponse50.getThirtyfourCount());
        assertEquals(48, changeResponse50.getThirtythreeCount());
        assertEquals(0, changeResponse50.getQuarterCount());
        assertEquals(1, changeResponse50.getDimeCount());
        assertEquals(1, changeResponse50.getNickelCount());
        assertEquals(1, changeResponse50.getPennyCount());
    }

    @Test
    void makeChange_Twenty_dollars_success() {
        ChangeResponse changeResponse20 = changeService.makeChange(TWENTY_DOLLAR_BILL);
        assertEquals(58, changeResponse20.getThirtyfourCount());
        assertEquals(0, changeResponse20.getThirtythreeCount());
        assertEquals(1, changeResponse20.getQuarterCount());
        assertEquals(0, changeResponse20.getDimeCount());
        assertEquals(0, changeResponse20.getNickelCount());
        assertEquals(3, changeResponse20.getPennyCount());
    }

    @Test
    void makeChange_Ten_dollars_success() {
        ChangeResponse changeResponse10 = changeService.makeChange(TEN_DOLLAR_BILL);
        assertEquals(29, changeResponse10.getThirtyfourCount());
        assertEquals(0, changeResponse10.getThirtythreeCount());
        assertEquals(0, changeResponse10.getQuarterCount());
        assertEquals(1, changeResponse10.getDimeCount());
        assertEquals(0, changeResponse10.getNickelCount());
        assertEquals(4, changeResponse10.getPennyCount());

    }

    @Test
    void makeChange_Five_dollars_success() {
        ChangeResponse changeResponse5 = changeService.makeChange(FIVE_DOLLAR_BILL);
        assertEquals(14, changeResponse5.getThirtyfourCount());
        assertEquals(0, changeResponse5.getThirtythreeCount());
        assertEquals(0, changeResponse5.getQuarterCount());
        assertEquals(2, changeResponse5.getDimeCount());
        assertEquals(0, changeResponse5.getNickelCount());
        assertEquals(4, changeResponse5.getPennyCount());

    }

    @Test
    void makeChange_Two_dollars_success() {
        ChangeResponse changeResponse2 = changeService.makeChange(TWO_DOLLAR_BILL);
        assertEquals(5, changeResponse2.getThirtyfourCount());
        assertEquals(0, changeResponse2.getThirtythreeCount());
        assertEquals(1, changeResponse2.getQuarterCount());
        assertEquals(0, changeResponse2.getDimeCount());
        assertEquals(1, changeResponse2.getNickelCount());
        assertEquals(0, changeResponse2.getPennyCount());

    }

    @Test
    void makeChange_One_dollar_success() {
        ChangeResponse changeResponse1 = changeService.makeChange(ONE_DOLLAR_BILL);
        assertEquals(2, changeResponse1.getThirtyfourCount());
        assertEquals(0, changeResponse1.getThirtythreeCount());
        assertEquals(1, changeResponse1.getQuarterCount());
        assertEquals(0, changeResponse1.getDimeCount());
        assertEquals(1, changeResponse1.getNickelCount());
        assertEquals(2, changeResponse1.getPennyCount());

    }

    @Test
    void fillDrawer_Coins_Of_Each() {
        changeService.fillDrawer(new FillDrawerRequest(600, 500, 400, 300, 200, 100));
        FillDrawerResponse fillDrawerResponse = changeService.getDrawerCounts();
        assertEquals(600, fillDrawerResponse.getThirtyfourCount());
        assertEquals(500, fillDrawerResponse.getThirtythreeCount());
        assertEquals(400, fillDrawerResponse.getQuarterCount());
        assertEquals(300, fillDrawerResponse.getDimeCount());
        assertEquals(200, fillDrawerResponse.getNickelCount());
        assertEquals(100, fillDrawerResponse.getPennyCount());
    }

}