package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DividendSummaryDto implements Serializable {
    private String tickerSymbol;
    private BigDecimal amountReceived;

    public DividendSummaryDto(String tickerSymbol, BigDecimal amountReceived) {
        this.tickerSymbol = tickerSymbol;
        this.amountReceived = amountReceived;
    }
}
