package click.divichart.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 配当情報集計Bean
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendSummaryBean implements Serializable {
    private String tickerSymbol;
    private BigDecimal amountReceived;

    public DividendSummaryBean(String tickerSymbol, BigDecimal amountReceived) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
    }
}
