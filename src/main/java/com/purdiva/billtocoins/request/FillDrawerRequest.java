package com.purdiva.billtocoins.request;

import com.purdiva.billtocoins.response.FillDrawerResponse;

public class FillDrawerRequest {

    private Integer pennyCount = 0;
    private Integer nickelCount = 0;
    private Integer dimeCount = 0;
    private Integer quarterCount = 0;

    public FillDrawerRequest() {}

    public FillDrawerRequest(Integer quarterCount, Integer dimeCount, Integer nickelCount, Integer pennyCount) {
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

    @Override
    public String toString() {
        return "FillDrawerRequest{" +
                "pennyCount=" + pennyCount +
                ", nickelCount=" + nickelCount +
                ", dimeCount=" + dimeCount +
                ", quarterCount=" + quarterCount +
                '}';
    }
}
