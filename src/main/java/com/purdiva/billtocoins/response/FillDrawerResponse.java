package com.purdiva.billtocoins.response;

public class FillDrawerResponse {

    private Integer pennyCount = 0;
    private Integer nickelCount = 0;
    private Integer dimeCount = 0;
    private Integer quarterCount = 0;

    public FillDrawerResponse() {}

    public FillDrawerResponse(Integer quarterCount, Integer dimeCount, Integer nickelCount, Integer pennyCount) {
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
}
