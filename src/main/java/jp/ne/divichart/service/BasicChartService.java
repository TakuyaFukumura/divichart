package jp.ne.divichart.service;

import jp.ne.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

@Service
public class BasicChartService {

    @Autowired
    DividendHistoryRepository repository;

    /**
     * 月別配当金額を取得する
     *
     * @param year 対象年
     * @return 月別配当配列
     */
    protected BigDecimal[] getMonthlyDividend(String year) {
        BigDecimal[] monthlyDividend = new BigDecimal[12];

        for (int i = 0; i < 12; i++) {
            int month = i + 1;
            String formattedMonth = String.format("%02d", month);

            LocalDate startDate = LocalDate.parse(year + "-" + formattedMonth + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate);
            monthlyDividend[i] = dividendSum;
        }
        return monthlyDividend;
    }

    /**
     * 受け取ったデータをグラフ描画用に合成する
     *
     * @param cumulativeDividend 合成したいデータ配列
     * @return 合成した文字列 例）"1,2,3,4,5"
     */
    protected String createChartData(BigDecimal[] cumulativeDividend) {
        StringJoiner chartData = new StringJoiner(",");
        for (BigDecimal dividend : cumulativeDividend) {
            chartData.add(dividend.toString());
        }
        return chartData.toString();
    }

    /**
     * 今年も含めて過去5年分の年（西暦）を取得する
     *
     * @return 年を表す配列
     */
    public String[] getRecentYears() {
        String[] recentYears = new String[5];
        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < 5; i++) {
            recentYears[i] = String.valueOf(currentYear - i);
        }
        return recentYears;
    }

    /**
     * 指定された数の最近の年を取得する。
     *
     * @param numOfYears 取得する年数
     * @return 最近の年の文字列配列
     */
    public String[] getRecentYears(int numOfYears) {
        String[] recentYears = new String[numOfYears];
        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < numOfYears; i++) {
            recentYears[i] = String.valueOf(currentYear - i);
        }
        return recentYears;
    }

}
