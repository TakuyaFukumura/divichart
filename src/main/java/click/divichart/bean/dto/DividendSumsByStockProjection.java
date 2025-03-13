package click.divichart.bean.dto;

import java.math.BigDecimal;

public interface DividendSumsByStockProjection {
    String getTickerSymbol();

    BigDecimal getAmountReceived();
}
