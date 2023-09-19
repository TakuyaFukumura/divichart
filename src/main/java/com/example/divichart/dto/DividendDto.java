package com.example.divichart.dto;

import java.math.BigDecimal;

public class DividendDto {
    private String tickerSymbol;
    private BigDecimal amountReceived;

    public DividendDto(String tickerSymbol, BigDecimal amountReceived) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }
}
