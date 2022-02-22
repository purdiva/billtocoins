package com.purdiva.billtocoins.response;

import com.purdiva.billtocoins.domain.Coin;

public class ChangeResponse {

    private Integer pennyCount = 0;
    private Integer nickelCount = 0;
    private Integer dimeCount = 0;
    private Integer quarterCount = 0;
    private Integer thirtythreeCount = 0;
    private Integer thirtyfourCount = 0;

    public ChangeResponse(){}

    public ChangeResponse(Integer thirtyfourCount, Integer thirtythreeCount, Integer quarterCount, Integer dimeCount, Integer nickelCount, Integer pennyCount) {
        this.thirtyfourCount = thirtyfourCount;
        this.thirtythreeCount = thirtythreeCount;
        this.quarterCount = quarterCount;
        this.dimeCount = dimeCount;
        this.nickelCount = nickelCount;
        this.pennyCount = pennyCount;
    }

    public Integer getPennyCount() {
        return pennyCount;
    }

    public Integer getNickelCount() {
        return nickelCount;
    }

    public Integer getDimeCount() {
        return dimeCount;
    }

    public Integer getQuarterCount() {
        return quarterCount;
    }

    public Integer getThirtythreeCount() { return thirtythreeCount; }

    public Integer getThirtyfourCount() { return thirtyfourCount; }

    public void setCoinCount(Coin coin, Integer coinCount) {
        switch (coin.getName().toLowerCase()) {
            case "thirtyfour":
                this.thirtyfourCount = coinCount;
                break;
            case "thirtythree":
                this.thirtythreeCount = coinCount;
                break;
            case "quarter":
                this.quarterCount = coinCount;
                break;
            case "dime":
                this.dimeCount = coinCount;
                break;
            case "nickel":
                this.nickelCount = coinCount;
                break;
            case "penny":
                this.pennyCount = coinCount;
                break;
        }
    }
}
