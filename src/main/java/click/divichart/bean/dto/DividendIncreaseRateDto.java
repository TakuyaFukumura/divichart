package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 配当増加率グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendIncreaseRateDto implements Serializable {
    private List<Integer> labels;
    private List<BigDecimal> chartData;

    public DividendIncreaseRateDto(List<Integer> labels, List<BigDecimal> chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
