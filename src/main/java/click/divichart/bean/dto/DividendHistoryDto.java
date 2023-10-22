package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class DividendHistoryDto implements Serializable {

    private Long id;
    private String tickerSymbol;
    private BigDecimal amountReceived;
    private Date receiptDate;

    public DividendHistoryDto(Long id, String tickerSymbol, BigDecimal amountReceived, Date receiptDate) {
        this.id = id;
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
        this.receiptDate = receiptDate;
    }
}
