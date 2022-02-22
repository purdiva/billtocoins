package com.purdiva.billtocoins.request;

public class FillDrawerRequest {

    private Integer pennyCount;
    private Integer nickelCount;
    private Integer dimeCount;
    private Integer quarterCount;
    private Integer thirtythreeCount;
    private Integer thirtyfourCount;

    public FillDrawerRequest(Integer thirtyfourCount, Integer thirtythreeCount, Integer quarterCount, Integer dimeCount, Integer nickelCount, Integer pennyCount) {
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
    @Override
    public String toString() {
        return "FillDrawerRequest{" +
                "pennyCount=" + pennyCount +
                ", nickelCount=" + nickelCount +
                ", dimeCount=" + dimeCount +
                ", quarterCount=" + quarterCount +
                ", thirtythreeCount=" + thirtythreeCount +
                ", thirtyfourCount=" + thirtyfourCount +
                '}';
    }
}
