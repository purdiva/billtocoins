package com.purdiva.billtocoins;

import com.purdiva.billtocoins.domain.Coin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
@SpringBootApplication
public class BilltocoinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilltocoinsApplication.class, args);
    }

//    @Component
//    public static class CashDrawer2 {
//
//        public static final Coin PENNY = new Coin("penny", new BigDecimal(0.01));
//        public static final Coin NICKEL = new Coin("nickel", new BigDecimal(0.05));
//        public static final Coin DIME = new Coin("dime", new BigDecimal(0.10));
//        public static final Coin QUARTER = new Coin("quarter", new BigDecimal(0.25));
//
//        private Map<Coin, Integer> coins = new HashMap<Coin, Integer>() {{
//            put(PENNY, 100);
//            put(NICKEL, 100);
//            put(DIME, 100);
//            put(QUARTER, 100);
//        }};
//
//
//        public Map<Coin, Integer> getCoins() {
//            return this.coins;
//        }
//
//        public void setCoins(Map<Coin, Integer> coins) {
//            this.coins = coins;
//        }
//
//        public Integer getTestCount() {
//            return 100;
//        }
//
//        public Integer getPennyCount() { return coins.get(PENNY); }
//
//        public Integer getNickelCount() {
//            return coins.get(NICKEL);
//        }
//
//        public Integer getDimeCount() {
//            return coins.get(DIME);
//        }
//
//        public Integer getQuarterCount() { return coins.get(QUARTER); }
//
//
//    }

}
