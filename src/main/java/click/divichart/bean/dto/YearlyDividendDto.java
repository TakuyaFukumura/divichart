package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 年別配当グラフ画面用DTO
 */
@Getter
@NoArgsConstructor
public class YearlyDividendDto implements Serializable {
    private String labels;
    private String chartData;

    public YearlyDividendDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
