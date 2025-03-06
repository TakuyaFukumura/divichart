package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class YearlyDividendService extends DividendService {

    public YearlyDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    public List<BigDecimal> getYearlyDividendData(List<Integer> pastYears, String username){
        List<BigDecimal> differences = new ArrayList<>();
        for (int targetYear : pastYears) {
            BigDecimal targetYearsDividend = getDividendSum(targetYear, username);
            differences.add(targetYearsDividend);
        }
        return differences;
    }

    /**
     * グラフ描画用に、年別配当データを取得する
     *
     * @param numOfYears 対象年数
     * @param username   ユーザ名
     * @return グラフ描画用文字列
     */
    public String getChartData(int numOfYears, String username) {
        String[] recentYears = getRecentYearsAsc(numOfYears);
        BigDecimal[] yearlyDividend = new BigDecimal[numOfYears];
        for (int i = 0; i < numOfYears; i++) {
            LocalDate startDate = LocalDate.parse(recentYears[i] + "-01-01");
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            yearlyDividend[i] = repository.getDividendSum(startDate, endDate, username);
        }
        return createChartData(yearlyDividend);
    }

    /**
     * グラフのラベルを取得する
     *
     * @param numOfYears 対象年数
     * @return グラフ描画用ラベル文字列
     */
    public String getLabels(int numOfYears) {
        String[] recentYears = getRecentYearsAsc(numOfYears);
        StringJoiner labels = new StringJoiner("年\",\"", "\"", "年\"");
        for (String year : recentYears) {
            labels.add(year);
        }
        return labels.toString();
    }
}
