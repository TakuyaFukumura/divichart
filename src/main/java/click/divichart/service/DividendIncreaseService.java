package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * 配当増加率グラフ用Service
 */
@Slf4j
@Service
public class DividendIncreaseService extends BasicChartService {

    public DividendIncreaseService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、指定年の配当増加率のデータを取得する
     *
     * @param recentYears 近年を表す文字列配列
     * @param username    ユーザ名
     * @return グラフ描画用文字列
     */
    public String getChartData(String[] recentYears, String username) {
        // 今年の配当額マイナス昨年の配当額
        BigDecimal[] differences = new BigDecimal[recentYears.length];

        for (int i = 0; i < differences.length; i++) {
            String targetYear = recentYears[differences.length - 1 - i];
            LocalDate targetYearStartDate = LocalDate.parse(targetYear + "-01-01");
            LocalDate targetYearEndDate = targetYearStartDate.plusYears(1).minusDays(1);
            BigDecimal targetYearsDividend = repository.getDividendSum(
                    targetYearStartDate,
                    targetYearEndDate,
                    username
            );

            LocalDate previousYearStartDate = targetYearStartDate.minusYears(1);
            LocalDate previousYearEndDate = previousYearStartDate.plusYears(1).minusDays(1);
            BigDecimal previousYearsDividend = repository.getDividendSum(
                    previousYearStartDate,
                    previousYearEndDate,
                    username
            );
            differences[i] = targetYearsDividend.subtract(previousYearsDividend);
        }
        return createChartData(differences);
    }

    /**
     * グラフのラベルを取得する
     *
     * @param recentYears 近年を表す文字列配列
     * @return グラフ描画用ラベル文字列
     */
    public String getLabels(String[] recentYears) {
        StringJoiner labels = new StringJoiner("年\",\"", "\"", "年\"");
        for (int i = recentYears.length - 1; i >= 0; i--) {
            String year = recentYears[i];
            labels.add(year);
        }
        return labels.toString();
    }
}
