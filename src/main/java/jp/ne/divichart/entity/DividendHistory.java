package jp.ne.divichart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class DividendHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tickerSymbol;
    private BigDecimal amountReceived;
    private Date receiptDate;

    protected DividendHistory() {
    }

    public DividendHistory(String tickerSymbol, BigDecimal amountReceived, Date receiptDate) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
    }

    public Long getId() {
        return id;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

}
