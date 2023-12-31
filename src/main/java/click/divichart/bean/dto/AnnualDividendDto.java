package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 年別配当グラフ画面用DTO
 */
@Getter
@NoArgsConstructor
public class AnnualDividendDto implements Serializable {
    private String labels;
    private String chartData;

    public AnnualDividendDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
