package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 配当達成率グラフ画面用DTO
 */
@Getter
@NoArgsConstructor
public class DividendAchievementRateDto implements Serializable {
    private List<String> labels;
    private List<BigDecimal> chartData;
    private String targetDividend;
    private String targetDividendYen;

    public DividendAchievementRateDto(List<String> labels, List<BigDecimal> chartData,
                                      String targetDividend, String targetDividendYen) {
        this.labels = labels;
        this.chartData = chartData;
        this.targetDividend = targetDividend;
        this.targetDividendYen = targetDividendYen;
    }
}
