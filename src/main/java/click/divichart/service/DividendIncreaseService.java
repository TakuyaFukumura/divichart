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
