package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * ChartService系で共通のロジックを持つクラス
 */
@Service
public class DividendService {

    private static final int TWELVE_MONTHS = 12;

    protected final DividendHistoryRepository repository;

    @Autowired
    public DividendService(DividendHistoryRepository dividendHistoryRepository) {
        this.repository = dividendHistoryRepository;
    }

    /**
     * 指定された過去の年数分の年をリストとして取得します。
     *
     * <p>現在の年を基準として、過去の年をリストで返します。
     * 例えば、現在の年が2025年で pastYearsCount が 3 の場合、
     * [2023, 2024, 2025] を返します。</p>
     *
     * @param pastYearsCount 過去の年数（1以上の整数）
     * @return 指定された年数分の過去の年を含むリスト（昇順）
     * @throws IllegalArgumentException pastYearsCount が 1 未満の場合
     */
    public List<Integer> getPastYears(int pastYearsCount) {
        if (pastYearsCount < 1) {
            throw new IllegalArgumentException("pastYearsCount must be at least 1");
        }
        int currentYear = LocalDate.now().getYear();
        int startYear = currentYear - pastYearsCount + 1;
        return IntStream.range(0, pastYearsCount)
                .mapToObj(i -> startYear + i)
                .toList();
    }

    /**
     * 月別配当金額を取得する
     *
     * @param targetYear 対象年
     * @param username   ユーザ名
     * @return 月別配当配列
     */
    protected BigDecimal[] getMonthlyDividend(String targetYear, String username) {
        BigDecimal[] monthlyDividend = new BigDecimal[TWELVE_MONTHS];

        for (int i = 0; i < TWELVE_MONTHS; i++) {
            int month = i + 1;
            String formattedMonth = String.format("%02d", month);

            LocalDate startDate = LocalDate.parse(targetYear + "-" + formattedMonth + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate, username);
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
     *
     * @param numOfYears 年数
     * @return 年を表す配列 例[ "2024","2023","2022" ]
     */
    public List<String> getRecentYears(int numOfYears) {
        int currentYear = LocalDate.now().getYear();
        return IntStream.range(0, numOfYears)
                .mapToObj(i -> String.valueOf(currentYear - i))
                .toList();
    }

    /**
     * 今年も含めて過去指定年数分の年（西暦）を昇順で取得する
     *
     * @param numOfYears 指定された年数
     * @return 年を表す配列 例[ "2022","2023","2024" ]
     */
    public String[] getRecentYearsAsc(int numOfYears) {
        int currentYear = LocalDate.now().getYear();
        return IntStream.range(0, numOfYears)
                .mapToObj(i -> String.valueOf(currentYear - numOfYears + 1 + i))
                .toArray(String[]::new);
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
     * @param currentYear 今年
     * @param targetYear  表示対象年
     * @return 表示対象年が不正であれば今年を返す。そうでなければ表示対象年をそのまま返す。
     */
    public String getTargetYear(String currentYear, String targetYear) {
        if (targetYear.isEmpty() || this.isNotYear(targetYear)) {
            return currentYear;
        }
        return targetYear;
    }
}
