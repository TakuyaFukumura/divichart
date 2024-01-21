package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 配当達成率グラフ画面用DTO
 */
@Getter
@NoArgsConstructor
public class DividendAchievementRateDto implements Serializable {
    private String labels;
    private String chartData;
    private String targetDividend;

    public DividendAchievementRateDto(String labels, String chartData, String targetDividend) {
        this.labels = labels;
        this.chartData = chartData;
        this.targetDividend = targetDividend;
    }
}
