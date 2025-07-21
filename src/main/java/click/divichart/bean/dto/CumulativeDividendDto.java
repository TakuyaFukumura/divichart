package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 累計配当グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class CumulativeDividendDto implements Serializable {

    private List<Integer> labels;
    private List<BigDecimal> chartData;

    public CumulativeDividendDto(List<Integer> labels, List<BigDecimal> chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
