package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 配当ポートフォリオ用DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class DividendPortfolioDto implements Serializable {
    private List<String> recentYears;
    private String targetYear;
    private String labels;
    private String chartData;

    public DividendPortfolioDto(String labels, String chartData) {
        this.labels = labels;
        this.chartData = chartData;
    }
    public DividendPortfolioDto(List<String> recentYears, String targetYear, String labels, String chartData) {
        this.recentYears = recentYears;
        this.targetYear = targetYear;
        this.labels = labels;
        this.chartData = chartData;
    }
}
