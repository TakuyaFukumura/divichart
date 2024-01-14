package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 累計配当グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class CumulativeDividendDto implements Serializable {

    private String labels;
    private String chartData;

    public CumulativeDividendDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
