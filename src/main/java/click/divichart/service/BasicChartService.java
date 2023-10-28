package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;
import java.util.regex.Pattern;

@Service
public class BasicChartService {

    private static final int TWELVE_MONTHS = 12;

    @Autowired
    DividendHistoryRepository repository;

    /**
     * 月別配当金額を取得する
     *
     * @param targetYear 対象年
     * @return 月別配当配列
     */
    protected BigDecimal[] getMonthlyDividend(String targetYear) {
        BigDecimal[] monthlyDividend = new BigDecimal[TWELVE_MONTHS];

        for (int i = 0; i < TWELVE_MONTHS; i++) {
            int month = i + 1;
            String formattedMonth = String.format("%02d", month);

            LocalDate startDate = LocalDate.parse(targetYear + "-" + formattedMonth + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate);
            monthlyDividend[i] = dividendSum;
        }
        return monthlyDividend;
    }

    /**
     * 受け取ったデータをグラフ描画用に合成する
     *
     * @param dividends 合成したいデータ配列
     * @return 合成した文字列 例）"1,2,3,4,5"
     */
    protected String createChartData(BigDecimal[] dividends) {
        StringJoiner chartData = new StringJoiner(",");
        for (BigDecimal dividend : dividends) {
            chartData.add(dividend.toString());
        }
        return chartData.toString();
    }

    /**
     * 今年も含めて過去指定年数分の年（西暦）を取得する
     * @param numOfYears 年数
     * @return 年を表す配列
     */
    public String[] getRecentYears(int numOfYears) {
        String[] recentYears = new String[numOfYears];
        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < numOfYears; i++) {
            recentYears[i] = String.valueOf(currentYear - i);
        }
        return recentYears;
    }

    /**
     * 引数が4桁の西暦年を表す文字列かどうかを判定します。
     *
     * @param year 年（例、2023）
     * @return 引数が西暦年を表す文字列ならtrue、そうでなければfalse
     */
    public boolean isNotYear(String year) {
        return !Pattern.matches("^[1-9]\\d{3}$", year);
    }

    /**
     * 表示対象年を取得する
     *
     * @param thisYear   今年
     * @param targetYear 表示対象年
     * @return 表示対象年が不正であれば今年を返す。そうでなければ表示対象年をそのまま返す。
     */
    public String getTargetYear(String currentYear, String targetYear) {
        if (targetYear.isEmpty() || this.isNotYear(targetYear)) {
            return currentYear;
        }
        return targetYear;
    }
}
