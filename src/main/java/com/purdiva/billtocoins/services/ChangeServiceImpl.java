package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.config.ApplicationProps;
import com.purdiva.billtocoins.exception.InvalidBillDenominationException;
import com.purdiva.billtocoins.exception.NotEnoughChangeException;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.util.CashDrawerUtils.CashDrawer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.purdiva.billtocoins.util.CashDrawerUtils.DIME;
import static com.purdiva.billtocoins.util.CashDrawerUtils.NICKEL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.PENNY;
import static com.purdiva.billtocoins.util.CashDrawerUtils.QUARTER;


@Service
public class ChangeServiceImpl implements ChangeService {

    @Autowired
    CashDrawer drw;

    @Autowired
    ApplicationProps applicationProps;

    private static final Logger log = LoggerFactory.getLogger(ChangeServiceImpl.class);
    private final MathContext mathContext = new MathContext(4);

    @Override
    public ChangeResponse makeChange(Integer bill) {
        if(validateDenomination(bill) && verifyEnoughAvailableInDrawer(bill)) {
            return calculateChange(bill);
        } else {
            return new ChangeResponse();
        }

    }

    @Override
    public void resetCashDrawer() {
        drw.resetCashDrawer();
    }

    private ChangeResponse calculateChange(Integer bill) {
        BigDecimal remainingBill = BigDecimal.valueOf(bill).round(mathContext);
        log.debug("Bill denomination to be changed ${}", remainingBill.round(mathContext));
        ChangeResponse changeResponse = new ChangeResponse(0, 0, 0, 0);

        final BigDecimal quarterValue = QUARTER.getValue();
        if(remainingBill.compareTo(quarterValue) > 0) {
            changeResponse.setQuarterCount(howManyQuarters(remainingBill));
            remainingBill = remainingBill.subtract(quarterValue.multiply(BigDecimal.valueOf(changeResponse.getQuarterCount()))).round(mathContext);
            log.debug("Bill amount remaining to be changed = {}", remainingBill.toString());
        }

        final BigDecimal dimeValue = DIME.getValue();
        if(remainingBill.compareTo(dimeValue) > 0) {
            changeResponse.setDimeCount(howManyDimes(remainingBill));
            remainingBill = remainingBill.subtract(dimeValue.multiply(BigDecimal.valueOf(changeResponse.getDimeCount()))).round(mathContext);
            log.debug("Bill amount remaining to be changed = {}", remainingBill.toString());
        }

        final BigDecimal nickelValue = NICKEL.getValue();
        if(remainingBill.compareTo(nickelValue) > 0) {
            changeResponse.setNickelCount(howManyNickels(remainingBill));
            remainingBill = remainingBill.subtract(nickelValue.multiply(BigDecimal.valueOf(changeResponse.getNickelCount()))).round(mathContext);
            log.debug("Bill amount remaining to be changed = {}", remainingBill.toString());
        }

        final BigDecimal pennyValue = PENNY.getValue();
        if(remainingBill.compareTo(pennyValue) > 0) {
            changeResponse.setPennyCount(howManyPennies(remainingBill));
            remainingBill = remainingBill.subtract(pennyValue.multiply(BigDecimal.valueOf(changeResponse.getPennyCount()))).round(mathContext);
            log.debug("Bill amount remaining to be changed = {}", remainingBill.toString());
        }

        return changeResponse;
    }

    private Integer howManyQuarters(BigDecimal billValue) {
        final Integer numQuartersAvailable = getQuarterCount();
        final BigDecimal quarterValueAvailable = QUARTER.getValue().multiply(BigDecimal.valueOf(numQuartersAvailable));
        if(quarterValueAvailable.compareTo(billValue) <= 0) {
            drw.setQuarterCount(0);
            return numQuartersAvailable;        //use all available quarters
        } else {
            final Integer numQuartersNeeded = billValue.divide(QUARTER.getValue(),2, RoundingMode.HALF_UP).intValue();
            drw.setQuarterCount(numQuartersAvailable - numQuartersNeeded);
            log.debug("Quarters remaining in drawer {}", getQuarterCount().toString());
            return numQuartersNeeded;
        }
    }

    private Integer howManyDimes(BigDecimal billValue) {
        final Integer numDimesAvailable = getDimeCount();
        final BigDecimal dimeValueAvailable = DIME.getValue().multiply(BigDecimal.valueOf(numDimesAvailable));
        if(dimeValueAvailable.compareTo(billValue) <= 0) {
            drw.setDimeCount(0);
            return numDimesAvailable;        //use all available dimes
        } else {
            final Integer numDimesNeeded = billValue.divide(DIME.getValue(),2, RoundingMode.HALF_UP).intValue();
            drw.setDimeCount(numDimesAvailable - numDimesNeeded);
            log.debug("Dimes remaining in drawer {}", getDimeCount().toString());
            return numDimesNeeded;
        }
    }

    private Integer howManyNickels(BigDecimal billValue) {
        final Integer numNickelsAvailable = getNickelCount();
        final BigDecimal nickelValueAvailable = NICKEL.getValue().multiply(BigDecimal.valueOf(numNickelsAvailable));
        if(nickelValueAvailable.compareTo(billValue) <= 0) {
            drw.setNickelCount(0);
            return numNickelsAvailable;        //use all available nickels
        } else {
            final Integer numNickelsNeeded = billValue.divide(NICKEL.getValue(),2, RoundingMode.HALF_UP).intValue();
            drw.setNickelCount(numNickelsAvailable - numNickelsNeeded);
            log.debug("Nickels remaining in drawer {}", getNickelCount().toString());
            return numNickelsNeeded;
        }
    }

    private Integer howManyPennies(BigDecimal billValue) {
        final Integer numPenniesAvailable = getPennyCount();
        final BigDecimal penniesValueAvailable = PENNY.getValue().multiply(BigDecimal.valueOf(numPenniesAvailable));
        if(penniesValueAvailable.compareTo(billValue.round(mathContext)) <= 0) {
            drw.setPennyCount(0);
            return numPenniesAvailable;        //use all available pennies
        } else {
            final Integer numPenniesNeeded = billValue.divide(PENNY.getValue(),2, RoundingMode.HALF_UP).intValue();
            drw.setPennyCount(numPenniesAvailable - numPenniesNeeded);
            log.debug("Pennies remaining in drawer {}", getPennyCount().toString());
            return numPenniesNeeded;
        }
    }

    private BigDecimal getDrawerTotal() {
        BigDecimal sum;
        sum = PENNY.getValue().multiply(BigDecimal.valueOf(getPennyCount()));
        sum = sum.add(NICKEL.getValue().multiply(BigDecimal.valueOf(getNickelCount())));
        sum = sum.add(DIME.getValue().multiply(BigDecimal.valueOf(getDimeCount())));
        sum = sum.add(QUARTER.getValue().multiply(BigDecimal.valueOf(getQuarterCount())));
        return sum;
    }

    private boolean validateDenomination(Integer denomination) {
        if(!applicationProps.getAccepted().stream()
                .anyMatch(denom -> (denom.intValue() == denomination))) {
            final String errMsgInvalid = "$" + denomination.toString() + " bills are not accepted at this machine.";
            log.error(errMsgInvalid);
            throw new InvalidBillDenominationException(errMsgInvalid);
        }
        return true;
    }

    private boolean verifyEnoughAvailableInDrawer(Integer bill) {
        BigDecimal availableChangeTotal = getDrawerTotal().round(mathContext);
        if (BigDecimal.valueOf(bill).compareTo(availableChangeTotal) > 0) {
            final String errMsgAmount = "Not enough money to change a $" + bill.toString() + " bill";
            log.error(errMsgAmount);
            throw new NotEnoughChangeException(errMsgAmount);
        }
        return true;
    }

    private Integer getQuarterCount() {
        return drw.getQuarterCount();
    }

    private Integer getDimeCount() {
        return drw.getDimeCount();
    }

    private Integer getNickelCount() {
        return drw.getNickelCount();
    }

    private Integer getPennyCount() {
        return drw.getPennyCount();
    }



}
