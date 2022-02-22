package com.purdiva.billtocoins.util;

import com.purdiva.billtocoins.domain.Coin;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CashDrawerUtils {

    public static final Coin PENNY = new Coin("penny", new BigDecimal("0.01"));
    public static final Coin NICKEL = new Coin("nickel", new BigDecimal("0.05"));
    public static final Coin DIME = new Coin("dime", new BigDecimal("0.10"));
    public static final Coin QUARTER = new Coin("quarter", new BigDecimal("0.25"));
    public static final Coin THIRTYTHREE = new Coin("thirtythree", new BigDecimal("0.33"));
    public static final Coin THIRTYFOUR = new Coin("thirtyfour", new BigDecimal("0.34"));
    public static final Integer HUNDRED_DOLLAR_BILL = 100;
    public static final Integer FIFTY_DOLLAR_BILL = 50;
    public static final Integer FORTY_DOLLAR_BILL = 40;
    public static final Integer TWENTY_DOLLAR_BILL = 20;
    public static final Integer TEN_DOLLAR_BILL = 10;
    public static final Integer FIVE_DOLLAR_BILL = 5;
    public static final Integer TWO_DOLLAR_BILL = 2;
    public static final Integer ONE_DOLLAR_BILL = 1;


    @Component
    public static class CashDrawer implements InitializingBean {

        @Value("${cashdrawer.initialize.thirtyfourcount}")
        Integer beginThirtyfourCount;

        @Value("${cashdrawer.initialize.thirtythreecount}")
        Integer beginThirtythreeCount;

        @Value("${cashdrawer.initialize.quartercount}")
        Integer beginQuarterCount;

        @Value("${cashdrawer.initialize.dimecount}")
        Integer beginDimeCount;

        @Value("${cashdrawer.initialize.nickelcount}")
        Integer beginNickelCount;

        @Value("${cashdrawer.initialize.pennycount}")
        Integer beginPennyCount;


        private final Map<Coin, Integer> coins = new HashMap<>() {{
            put(THIRTYFOUR, beginThirtyfourCount);
            put(THIRTYTHREE, beginThirtythreeCount);
            put(QUARTER, beginQuarterCount);
            put(DIME, beginDimeCount);
            put(NICKEL, beginNickelCount);
            put(PENNY, beginPennyCount);
        }};

        public Integer getPennyCount() { return coins.get(PENNY); }

        public void setPennyCount(Integer pennyCount) { coins.put(PENNY, pennyCount); }

        public Integer getNickelCount() {
            return coins.get(NICKEL);
        }

        public void setNickelCount(Integer nickelCount) { coins.put(NICKEL, nickelCount); }

        public Integer getDimeCount() {
            return coins.get(DIME);
        }

        public void setDimeCount(Integer dimeCount) { coins.put(DIME, dimeCount); }

        public Integer getQuarterCount() { return coins.get(QUARTER); }

        public void setQuarterCount(Integer quarterCount) { coins.put(QUARTER, quarterCount); }

        public Integer getThirtythreeCount() { return coins.get(THIRTYTHREE); }

        public void setThirtythreeCount(Integer thirtythreeCount) { coins.put(THIRTYTHREE, thirtythreeCount); }

        public Integer getThirtyfourCount() { return coins.get(THIRTYFOUR); }

        public void setThirtyfourCount(Integer thirtyfourCount) { coins.put(THIRTYFOUR, thirtyfourCount); }

        public void resetCashDrawer() {
            setThirtyfourCount(beginThirtyfourCount);
            setThirtythreeCount(beginThirtythreeCount);
            setQuarterCount(beginQuarterCount);
            setDimeCount(beginDimeCount);
            setNickelCount(beginNickelCount);
            setPennyCount(beginPennyCount);
        }

        public Integer getCoinCountByType(Coin coin) {
            return coins.get(coin).intValue();
        }

        public void setCoinCountByType(Coin coin, Integer countCoins) {
            this.coins.put(coin, countCoins);
        }

        @Override
        public void afterPropertiesSet() {
            resetCashDrawer();
        }
    }
}
