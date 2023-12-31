package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 配当ポートフォリオ用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendPortfolioDto implements Serializable {
    private String[] recentYears;
    private String targetYear;
    private String labels;
    private String chartData;

    public DividendPortfolioDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
}
