package com.purdiva.billtocoins.services;

import com.purdiva.billtocoins.config.ApplicationProps;
import com.purdiva.billtocoins.domain.Coin;
import com.purdiva.billtocoins.exception.InvalidBillDenominationException;
import com.purdiva.billtocoins.exception.NotEnoughChangeException;
import com.purdiva.billtocoins.request.FillDrawerRequest;
import com.purdiva.billtocoins.response.ChangeResponse;
import com.purdiva.billtocoins.response.FillDrawerResponse;
import com.purdiva.billtocoins.util.CashDrawerUtils.CashDrawer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static com.purdiva.billtocoins.util.CashDrawerUtils.DIME;
import static com.purdiva.billtocoins.util.CashDrawerUtils.NICKEL;
import static com.purdiva.billtocoins.util.CashDrawerUtils.PENNY;
import static com.purdiva.billtocoins.util.CashDrawerUtils.QUARTER;
import static com.purdiva.billtocoins.util.CashDrawerUtils.THIRTYFOUR;
import static com.purdiva.billtocoins.util.CashDrawerUtils.THIRTYTHREE;


@Service
public class ChangeServiceImpl implements ChangeService {

    private final CashDrawer drw;
    private final ApplicationProps applicationProps;

    public ChangeServiceImpl(CashDrawer drw, ApplicationProps applicationProps) {
        Assert.notNull(drw, "CashDrawer cannot be null");
        Assert.notNull(applicationProps, "Application Properties cannot be null");
        this.drw = drw;
        this.applicationProps = applicationProps;
    }

    private static final Logger log = LoggerFactory.getLogger(ChangeServiceImpl.class);
    private final MathContext mathContext = new MathContext(4);
    private final List<Coin> coins = Arrays.asList(THIRTYFOUR, THIRTYTHREE, QUARTER, DIME, NICKEL, PENNY);

    @Override
    public ChangeResponse makeChange(Integer bill) {
        if(validateDenomination(bill) && verifyEnoughAvailableInDrawer(bill)) {
            return calculateChange(bill);
        } else {
            return new ChangeResponse();
        }
    }

    @Override
    public void fillDrawer(FillDrawerRequest fillDrawerRequest) {
        drw.setThirtyfourCount(fillDrawerRequest.getThirtyfourCount());
        drw.setThirtythreeCount(fillDrawerRequest.getThirtythreeCount());
        drw.setQuarterCount(fillDrawerRequest.getQuarterCount());
        drw.setDimeCount(fillDrawerRequest.getDimeCount());
        drw.setNickelCount(fillDrawerRequest.getNickelCount());
        drw.setPennyCount(fillDrawerRequest.getPennyCount());
    }

    @Override
    public FillDrawerResponse getDrawerCounts() {
        return new FillDrawerResponse(drw.getThirtyfourCount(),drw.getThirtythreeCount(),drw.getQuarterCount(), drw.getDimeCount(), drw.getNickelCount(), drw.getPennyCount());
    }

    private ChangeResponse calculateChange(Integer bill) {
        BigDecimal remainingBillValue = BigDecimal.valueOf(bill).round(mathContext);
        ChangeResponse resultChangeResponse = new ChangeResponse();

        for(Coin coin : coins) {
            if(remainingBillValue.compareTo(BigDecimal.ZERO) > 0) {
                Integer coinsNeeded = calculateNumberOfCoinsByCoinType(coin, remainingBillValue);
                removeCoinsFromDrawer(coin, coinsNeeded);
                remainingBillValue = remainingBillValue.subtract(coin.getValue().multiply(BigDecimal.valueOf(coinsNeeded)));
                resultChangeResponse.setCoinCount(coin, coinsNeeded);
            }
        }
        return resultChangeResponse;
    }

    private Integer calculateNumberOfCoinsByCoinType(Coin coin, BigDecimal remainingBill) {
        final Integer numCoinsAvailable = drw.getCoinCountByType(coin);
        final BigDecimal coinValueAvailable = coin.getValue().multiply(BigDecimal.valueOf(numCoinsAvailable));
        if(coinValueAvailable.compareTo(remainingBill) <= 0) {
            return numCoinsAvailable;        //use all available of this coin
        } else {
            return remainingBill.divide(coin.getValue(), 2, RoundingMode.HALF_UP).intValue();
        }
    }

    private Integer removeCoinsFromDrawer(Coin coin, Integer numberOfCoinsRemoved) {
        final Integer numCoinsAvailable = drw.getCoinCountByType(coin);
        final Integer coinCountRemaining = numCoinsAvailable - numberOfCoinsRemoved;
        drw.setCoinCountByType(coin, coinCountRemaining);
        log.debug("{}(s) remaining in drawer {}", coin.getName(), coinCountRemaining.toString());
        return coinCountRemaining;
    }

    private BigDecimal getDrawerTotalValue() {
        BigDecimal sum = getDrawerSlotTotalValue(THIRTYFOUR);
        sum = sum.add(getDrawerSlotTotalValue(THIRTYTHREE));
        sum = sum.add(getDrawerSlotTotalValue(QUARTER));
        sum = sum.add(getDrawerSlotTotalValue(DIME));
        sum = sum.add(getDrawerSlotTotalValue(NICKEL));
        sum = sum.add(getDrawerSlotTotalValue(PENNY));
        return sum;
    }

    private BigDecimal getDrawerSlotTotalValue(Coin coin) {
        return coin.getValue().multiply(BigDecimal.valueOf(drw.getCoinCountByType(coin)));
    }

    private boolean validateDenomination(Integer denomination) {
        if(applicationProps.getAccepted().stream()
                .noneMatch(denom -> (denom.intValue() == denomination))) {
            final String errMsgInvalid = "$" + denomination.toString() + " bills are not accepted at this machine.";
            log.error(errMsgInvalid);
            throw new InvalidBillDenominationException(errMsgInvalid);
        }
        return true;
    }

    private boolean verifyEnoughAvailableInDrawer(Integer bill) {
        BigDecimal availableChangeTotal = getDrawerTotalValue().round(mathContext);
        if (BigDecimal.valueOf(bill).compareTo(availableChangeTotal) > 0) {
            final String errMsgAmount = "Not enough money to change a $" + bill.toString() + " bill";
            log.error(errMsgAmount);
            throw new NotEnoughChangeException(errMsgAmount);
        }
        return true;
    }
}
