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
}
