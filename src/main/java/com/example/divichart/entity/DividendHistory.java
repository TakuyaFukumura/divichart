package com.example.divichart.entity;

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
    private BigDecimal amountReceived;
    private Date receiptDate;

    protected DividendHistory() {
    }

    public DividendHistory(BigDecimal amountReceived, Date receiptDate) {
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

}
