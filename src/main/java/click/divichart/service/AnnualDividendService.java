package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

@Service
public class AnnualDividendService extends BasicChartService {

    @Autowired
    DividendHistoryRepository repository;

    /**
     * グラフ描画用に、年別配当データを取得する
     *
     * @param numOfYears 対象年数
     * @return グラフ描画用文字列
     */
    public String getChartData(int numOfYears) {
        String[] recentYears = getRecentYearsAsc(numOfYears);
        BigDecimal[] annualDividend = new BigDecimal[numOfYears];
        for (int i = 0; i < numOfYears; i++) {
            LocalDate startDate = LocalDate.parse(recentYears[i] + "-01-01");
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            annualDividend[i] = repository.getDividendSum(startDate, endDate);
        }
        return createChartData(annualDividend);
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

    /**
     * 今年も含めて過去指定年数分の年（西暦）を昇順で取得する
     *
     * @param numOfYears 指定された年数
     * @return 年を表す配列
     */
    public String[] getRecentYearsAsc(int numOfYears) {
        String[] recentYears = new String[numOfYears];
        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < numOfYears; i++) {
            int reverseIndex = numOfYears - 1 - i;
            int year = currentYear - i;
            recentYears[reverseIndex] = String.valueOf(year);
        }
        return recentYears;
    }

}
