package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 配当増加額グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendIncreaseDto implements Serializable {
    private String labels;
    private String chartData;

    public DividendIncreaseDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
