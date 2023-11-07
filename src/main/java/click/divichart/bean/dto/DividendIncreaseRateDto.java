package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 配当増加率グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendIncreaseRateDto implements Serializable {
    private String labels;
    private String chartData;

    public DividendIncreaseRateDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
