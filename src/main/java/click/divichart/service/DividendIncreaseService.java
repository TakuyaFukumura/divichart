package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

/**
 * 配当増加額グラフ用Service
 */
@Slf4j
@Service
public class DividendIncreaseService extends DividendService {

    public DividendIncreaseService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
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
     * 過去の年ごとの配当増加額を計算してリストで返すメソッドです。
     *
     * @param pastYears 対象となる過去の年のリスト
     * @param username  ユーザー名。配当のデータを取得する際に使用されます。
     * @return 各年の配当金の増加額を表すBigDecimalのリスト
     */
    public List<BigDecimal> getDividendIncreaseData(List<Integer> pastYears, String username) {
        List<BigDecimal> differences = new ArrayList<>();
        for (int targetYear : pastYears) {
            BigDecimal previousYearsDividend = getDividendSum(targetYear - 1, username);
            BigDecimal targetYearsDividend = getDividendSum(targetYear, username);
            BigDecimal difference = targetYearsDividend.subtract(previousYearsDividend);
            differences.add(difference);
        }
        return differences;
    }

    /**
     * 特定の年の配当金合計を取得するメソッドです。
     *
     * @param year     対象となる年
     * @param username ユーザー名。配当のデータを取得する際に使用されます。
     * @return 指定した年の配当金合計
     */
    private BigDecimal getDividendSum(int year, String username) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return repository.getDividendSum(startDate, endDate, username);
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
}
