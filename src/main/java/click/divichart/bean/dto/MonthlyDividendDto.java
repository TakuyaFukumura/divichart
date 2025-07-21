package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 月別配当グラフ画面用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class MonthlyDividendDto implements Serializable {
    private List<String> recentYears;
    private String targetYear;
    private List<BigDecimal> chartData;

    public MonthlyDividendDto(List<String> recentYears, String targetYear, List<BigDecimal> chartData) {
        this.recentYears = recentYears;
        this.targetYear = targetYear;
        this.chartData = chartData;
    }
}
