package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PieChartDto implements Serializable {
    private String tickerSymbolData;
    private String amountReceivedData;

    public PieChartDto(String tickerSymbolData, String amountReceivedData) {
        this.tickerSymbolData = tickerSymbolData;
        this.amountReceivedData = amountReceivedData;
    }
}
