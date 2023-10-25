package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LineChartDto implements Serializable {
    private String[] recentYears;
    private String targetYear;
    private String chartData;

    public LineChartDto(String[] recentYears, String targetYear, String chartData) {
        this.recentYears = recentYears;
        this.targetYear = targetYear;
        this.chartData = chartData;
    }
}
