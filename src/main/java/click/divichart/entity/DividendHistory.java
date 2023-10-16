package click.divichart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
public class DividendHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tickerSymbol;
    private BigDecimal amountReceived;
    private Date receiptDate;

    public DividendHistory(String tickerSymbol, BigDecimal amountReceived, Date receiptDate) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
    }
}
