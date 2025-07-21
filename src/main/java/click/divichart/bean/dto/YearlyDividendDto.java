package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 年別配当グラフ画面用DTO
 */
@Getter
@NoArgsConstructor
public class YearlyDividendDto implements Serializable {
    private List<Integer> labels;
    private List<BigDecimal> chartData;

    public YearlyDividendDto(List<Integer> labels, List<BigDecimal> chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
