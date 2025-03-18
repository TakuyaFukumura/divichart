package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CumulativeDividendService extends DividendService {

    public CumulativeDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、近年の配当累計データを取得する
     *
     * @param pastYears 近年
     * @param username  ユーザ名
     * @return グラフ描画用文字列
     */
    public List<BigDecimal> getCumulativeDividendData(List<Integer> pastYears, String username) {
        List<BigDecimal> yearlyDividends = new ArrayList<>();
        for (int targetYear : pastYears) {
            LocalDate targetYearStartDate = LocalDate.of(targetYear, 1, 1);
            LocalDate targetYearEndDate = LocalDate.of(targetYear, 12, 31);
            BigDecimal targetYearsDividend = repository.getDividendSum(targetYearStartDate, targetYearEndDate, username);
            yearlyDividends.add(targetYearsDividend);
        }
        return getCumulativeDividend(yearlyDividends);
    }

    /**
     * 累計額の配列にして返す
     *
     * @param yearlyDividend 年別配当
     * @return 累計配当
     */
    List<BigDecimal> getCumulativeDividend(List<BigDecimal> yearlyDividend) {
        List<BigDecimal> cumulativeDividend = new ArrayList<>(yearlyDividend);

        for (int i = 1; i < cumulativeDividend.size(); i++) {
            cumulativeDividend.set(i, cumulativeDividend.get(i).add(cumulativeDividend.get(i - 1)));
        }
        return cumulativeDividend;
    }
}
