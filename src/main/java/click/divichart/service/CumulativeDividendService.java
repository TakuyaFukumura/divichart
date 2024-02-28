package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.StringJoiner;

@Service
public class CumulativeDividendService extends BasicChartService {

    public CumulativeDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、近年の配当累計データを取得する
     *
     * @return グラフ描画用文字列
     */
    public String getChartData(String[] recentYears, String username) {
        int length = recentYears.length;
        BigDecimal[] yearlyDividend = new BigDecimal[length];
        for (int i = 0; i < length; i++) {
            String targetYear = recentYears[i];
            LocalDate targetYearStartDate = LocalDate.parse(targetYear + "-01-01");
            LocalDate targetYearEndDate = targetYearStartDate.plusYears(1).minusDays(1);
            BigDecimal targetYearsDividend = repository.getDividendSum(targetYearStartDate, targetYearEndDate, username);
            yearlyDividend[i] = targetYearsDividend;
        }
        BigDecimal[] cumulativeDividend = getCumulativeDividend(yearlyDividend);
        return createChartData(cumulativeDividend);
    }

    /**
     * 累計額の配列にして返す
     *
     * @param yearlyDividend 年別配当
     * @return 累計配当
     */
    BigDecimal[] getCumulativeDividend(BigDecimal[] yearlyDividend) {
        BigDecimal[] cumulativeDividend = Arrays.copyOf(yearlyDividend, yearlyDividend.length);

        for (int i = 1; i < cumulativeDividend.length; i++) {
            cumulativeDividend[i] = cumulativeDividend[i].add(cumulativeDividend[i - 1]);
        }
        return cumulativeDividend;
    }

    /**
     * グラフのラベルを取得する
     *
     * @param recentYears 近年を表す文字列配列
     * @return グラフ描画用ラベル文字列
     */
    public String getLabels(String[] recentYears) {
        StringJoiner labels = new StringJoiner("年\",\"", "\"", "年\"");
        for (String year : recentYears) {
            labels.add(year);
        }
        return labels.toString();
    }

}
