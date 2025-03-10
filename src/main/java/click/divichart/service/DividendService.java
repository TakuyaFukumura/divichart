package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * 指定された過去の年リストから、年を「年」というフォーマットで結合し、カンマ区切りの文字列を返します。
     * <p>
     * 例えば、リストに「2021, 2022, 2023」が含まれている場合、返される文字列は
     * "\"2021年\",\"2022年\",\"2023年\"" となります。
     * </p>
     * <p>
     * 引数が空リストや null の場合、IllegalArgumentException がスローされます。
     * </p>
     *
     * @param pastYears 過去の年を表す整数のリスト。空でないリストが渡される必要があります。
     * @return 年を「年」という形式で結合した文字列
     * @throws IllegalArgumentException 引数として空のリストまたは null が渡された場合にスローされます。
     */
    public String getLabels(List<Integer> pastYears) {
        if (pastYears == null || pastYears.isEmpty()) {
            throw new IllegalArgumentException("過去の年のリストが空です");
        }
        StringJoiner labels = new StringJoiner("年\",\"", "\"", "年\"");
        for (int year : pastYears) {
            labels.add(String.valueOf(year));
        }
        return labels.toString();
    }

    /**
     * 特定の年の配当金合計を取得するメソッドです。
     *
     * @param year     対象となる年
     * @param username ユーザー名。配当のデータを取得する際に使用されます。
     * @return 指定した年の配当金合計
     */
    public BigDecimal getDividendSum(int year, String username) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return repository.getDividendSum(startDate, endDate, username);
    }

    protected List<BigDecimal> getMonthlyDividend(int targetYear, String username) {
        List<BigDecimal> monthlyDividend = new ArrayList<>(TWELVE_MONTHS);

        for (int i = 0; i < TWELVE_MONTHS; i++) {
            int month = i + 1;
            String formattedMonth = String.format("%02d", month);

            LocalDate startDate = LocalDate.parse(targetYear + "-" + formattedMonth + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate, username);
            monthlyDividend.add(dividendSum);
        }
        return monthlyDividend;
    }

    /**
     * 指定された配当増加データリストをカンマ区切りの文字列に変換します。
     *
     * @param dividendIncreaseData 配当増加のデータリスト。各要素は {@link BigDecimal} 型です。
     * @return 配当増加データリストをカンマ区切りで結合した文字列。
     * 例: "1.2,3.4,5.6"
     */
    public String createChartData(List<BigDecimal> dividendIncreaseData) {
        StringJoiner chartData = new StringJoiner(",");
        for (BigDecimal dividend : dividendIncreaseData) {
            chartData.add(dividend.toString());
        }
        return chartData.toString();
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
