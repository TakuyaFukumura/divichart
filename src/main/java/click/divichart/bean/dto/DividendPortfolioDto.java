package click.divichart.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 配当ポートフォリオ用DTO
 */
@Data
@AllArgsConstructor
public class DividendPortfolioDto implements Serializable {
    private List<String> recentYears;
    private String targetYear;
    private String labels;
    private String chartData;
}
