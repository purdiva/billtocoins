package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.exception.InvalidBillDenominationException;
import com.purdiva.billtocoins.exception.NotEnoughChangeException;
import com.purdiva.billtocoins.response.ChangeResponse;
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
    void makeChange_tooLargeBill_Hundred_Dollar_Bill_throws_NotEnoughChangeException() {
        Exception exception = assertThrows(NotEnoughChangeException.class, () -> changeService.makeChange(HUNDRED_DOLLAR_BILL));
        final String expectedMessage = "Not enough money to change a $100 bill";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void makeChange_tooLargeBill_Fifty_Dollar_Bill_throws_NotEnoughChangeException() {
        Exception exception = assertThrows(NotEnoughChangeException.class, () -> changeService.makeChange(FIFTY_DOLLAR_BILL));
        final String expectedMessage = "Not enough money to change a $50 bill";
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
    void makeChange_multipleBills_Twenty_And_Ten_And_Five_And_One_success() {
        ChangeResponse changeResponse20 = changeService.makeChange(TWENTY_DOLLAR_BILL);
        assertEquals(80, changeResponse20.getQuarterCount());
        assertEquals(0, changeResponse20.getDimeCount());
        assertEquals(0, changeResponse20.getNickelCount());
        assertEquals(0, changeResponse20.getPennyCount());

        ChangeResponse changeResponse10 = changeService.makeChange(TEN_DOLLAR_BILL);
        assertEquals(20, changeResponse10.getQuarterCount());
        assertEquals(50, changeResponse10.getDimeCount());
        assertEquals(0, changeResponse10.getNickelCount());
        assertEquals(0, changeResponse10.getPennyCount());

        ChangeResponse changeResponse5 = changeService.makeChange(FIVE_DOLLAR_BILL);
        assertEquals(0, changeResponse5.getQuarterCount());
        assertEquals(50, changeResponse5.getDimeCount());
        assertEquals(0, changeResponse5.getNickelCount());
        assertEquals(0, changeResponse5.getPennyCount());

        ChangeResponse changeResponse2 = changeService.makeChange(TWO_DOLLAR_BILL);
        assertEquals(0, changeResponse2.getQuarterCount());
        assertEquals(0, changeResponse2.getDimeCount());
        assertEquals(40, changeResponse2.getNickelCount());
        assertEquals(0, changeResponse2.getPennyCount());

        ChangeResponse changeResponseMoreNickels = changeService.makeChange(TWO_DOLLAR_BILL);
        assertEquals(0, changeResponseMoreNickels.getQuarterCount());
        assertEquals(0, changeResponseMoreNickels.getDimeCount());
        assertEquals(40, changeResponseMoreNickels.getNickelCount());
        assertEquals(0, changeResponseMoreNickels.getPennyCount());

        ChangeResponse changeResponseFinal20Nickels = changeService.makeChange(ONE_DOLLAR_BILL);
        assertEquals(0, changeResponseFinal20Nickels.getQuarterCount());
        assertEquals(0, changeResponseFinal20Nickels.getDimeCount());
        assertEquals(20, changeResponseFinal20Nickels.getNickelCount());
        assertEquals(0, changeResponseFinal20Nickels.getPennyCount());

        ChangeResponse changeResponse100Pennies = changeService.makeChange(ONE_DOLLAR_BILL);
        assertEquals(0, changeResponse100Pennies.getQuarterCount());
        assertEquals(0, changeResponse100Pennies.getDimeCount());
        assertEquals(0, changeResponse100Pennies.getNickelCount());
        assertEquals(100, changeResponse100Pennies.getPennyCount());
    }

    @Test
    void makeChange_Change_Twenty_Three_Times_Not_Enough_Change_For_Third_Bill_fail() {

        ChangeResponse changeResponse20a = changeService.makeChange(TWENTY_DOLLAR_BILL);
        assertEquals(80, changeResponse20a.getQuarterCount());
        assertEquals(0, changeResponse20a.getDimeCount());
        assertEquals(0, changeResponse20a.getNickelCount());
        assertEquals(0, changeResponse20a.getPennyCount());

        ChangeResponse changeResponse20b = changeService.makeChange(TWENTY_DOLLAR_BILL);
        assertEquals(20, changeResponse20b.getQuarterCount());
        assertEquals(100, changeResponse20b.getDimeCount());
        assertEquals(100, changeResponse20b.getNickelCount());
        assertEquals(0, changeResponse20b.getPennyCount());

        Exception exception = assertThrows(NotEnoughChangeException.class, () -> changeService.makeChange(TWENTY_DOLLAR_BILL));
        final String expectedMessage = "Not enough money to change a $20 bill";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

}