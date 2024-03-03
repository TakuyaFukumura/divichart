package click.divichart.bean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 配当履歴テーブル用エンティティ
 * dividend_history
 */
@Entity
@Getter
@NoArgsConstructor
public class DividendHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tickerSymbol;
    private BigDecimal amountReceived;
    private Date receiptDate;
    private String username;

    public DividendHistory(String username) {
        this.username = username;
    }

    public DividendHistory(String tickerSymbol, BigDecimal amountReceived, Date receiptDate, String username) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
        this.username = username;
    }

    public DividendHistory(Long id, String tickerSymbol, BigDecimal amountReceived, Date receiptDate, String username) {
        this.id = id;
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
        this.username = username;
    }
}
