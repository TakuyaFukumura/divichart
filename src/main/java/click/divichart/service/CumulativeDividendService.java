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
     * グラフ描画用に、指定年範囲の配当累計データを取得する
     *
     * @param pastYears 指定年範囲
     * @param username  ユーザ名
     * @return グラフ描画用文字列
     */
    public List<BigDecimal> getCumulativeDividendData(List<Integer> pastYears, String username) {
        List<BigDecimal> cumulativeDividend = new ArrayList<>();

        BigDecimal previousSum = BigDecimal.ZERO;
        for (int targetYear : pastYears) {
            LocalDate targetYearStartDate = LocalDate.of(targetYear, 1, 1);
            LocalDate targetYearEndDate = LocalDate.of(targetYear, 12, 31);
            BigDecimal targetYearsDividend = repository.getDividendSum(targetYearStartDate, targetYearEndDate, username);

            // 累積配当金を計算してリストに追加
            previousSum = previousSum.add(targetYearsDividend);
            cumulativeDividend.add(previousSum);
        }

        return cumulativeDividend;
    }
}
