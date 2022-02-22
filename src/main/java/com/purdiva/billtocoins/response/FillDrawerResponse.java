package com.purdiva.billtocoins.response;

public class FillDrawerResponse {

    private Integer pennyCount = 0;
    private Integer nickelCount = 0;
    private Integer dimeCount = 0;
    private Integer quarterCount = 0;
    private Integer thirtythreeCount = 0;
    private Integer thirtyfourCount = 0;

    public FillDrawerResponse() {}

    public FillDrawerResponse(Integer thirtyfourCount, Integer thirtythreeCount, Integer quarterCount, Integer dimeCount, Integer nickelCount, Integer pennyCount) {
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

    public void setPennyCount(Integer pennyCount) {
        this.pennyCount = pennyCount;
    }

    public Integer getNickelCount() {
        return nickelCount;
    }

    public void setNickelCount(Integer nickelCount) {
        this.nickelCount = nickelCount;
    }

    public Integer getDimeCount() {
        return dimeCount;
    }

    public void setDimeCount(Integer dimeCount) {
        this.dimeCount = dimeCount;
    }

    public Integer getQuarterCount() {
        return quarterCount;
    }

    public void setQuarterCount(Integer quarterCount) {
        this.quarterCount = quarterCount;
    }

    public Integer getThirtythreeCount() { return thirtythreeCount; }

    public void setThirtythreeCount(Integer thirtythreeCount) {
        this.thirtythreeCount = thirtythreeCount;
    }

    public Integer getThirtyfourCount() { return thirtyfourCount; }

    public void setThirtyfourCount(Integer thirtyfourCount) {
        this.thirtyfourCount = thirtyfourCount;
    }
}
