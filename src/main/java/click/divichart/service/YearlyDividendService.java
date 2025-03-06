package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class YearlyDividendService extends DividendService {

    public YearlyDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * 指定された過去の各年に対する配当金の合計を取得し、それをリストとして返します。
     *
     * @param pastYears 過去の年を表す整数のリスト。このリストに含まれる各年について、対応する配当金の合計が計算されます。
     * @param username  配当金情報を取得する対象のユーザー名。
     * @return 指定された各年における配当金の合計を格納したリスト。リストの要素は {@link BigDecimal} 型で、各年の配当金合計を表します。
     */
    public List<BigDecimal> getYearlyDividendData(List<Integer> pastYears, String username) {
        List<BigDecimal> differences = new ArrayList<>();
        for (int targetYear : pastYears) {
            BigDecimal targetYearsDividend = getDividendSum(targetYear, username);
            differences.add(targetYearsDividend);
        }
        return differences;
    }
}
