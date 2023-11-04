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
    private String[] recentYears;
    private String targetYear;
    private String chartData;

    public CumulativeDividendDto(String[] recentYears, String targetYear, String chartData) {
        this.recentYears = recentYears;
        this.targetYear = targetYear;
        this.chartData = chartData;
    }
}
