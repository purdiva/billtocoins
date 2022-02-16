package com.purdiva.billtocoins.util;

import com.purdiva.billtocoins.domain.Coin;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CashDrawerUtils {

    public static final Coin PENNY = new Coin("penny", new BigDecimal(0.01));
    public static final Coin NICKEL = new Coin("nickel", new BigDecimal(0.05));
    public static final Coin DIME = new Coin("dime", new BigDecimal(0.10));
    public static final Coin QUARTER = new Coin("quarter", new BigDecimal(0.25));
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

        @Value("${cashdrawer.initialize.quartercount}")
        Integer beginQuarterCount;

        @Value("${cashdrawer.initialize.dimecount}")
        Integer beginDimeCount;

        @Value("${cashdrawer.initialize.nickelcount}")
        Integer beginNickelCount;

        @Value("${cashdrawer.initialize.pennycount}")
        Integer beginPennyCount;


        private Map<Coin, Integer> coins = new HashMap<>() {{
            put(QUARTER, beginQuarterCount);
            put(DIME, beginDimeCount);
            put(NICKEL, beginNickelCount);
            put(PENNY, beginPennyCount);
        }};


        public Map<Coin, Integer> getCoins() {
            return this.coins;
        }

        public void setCoins(Map<Coin, Integer> coins) {
            this.coins = coins;
        }

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

        public void resetCashDrawer() {
            setQuarterCount(beginQuarterCount);
            setDimeCount(beginDimeCount);
            setNickelCount(beginNickelCount);
            setPennyCount(beginPennyCount);
        }

        @Override
        public void afterPropertiesSet() {
            resetCashDrawer();
        }
    }
}
