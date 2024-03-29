package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class YearlyCumulativeDividendService extends BasicChartService {

    public YearlyCumulativeDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、指定年の1月～12月までの配当累計を計算する
     *
     * @param targetYear データ作成対象年
     * @param username   ユーザ名
     * @return グラフ描画用文字列
     */
    public String getChartData(String targetYear, String username) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(targetYear, username);
        BigDecimal[] cumulativeDividend = getCumulativeDividend(monthlyDividend);
        return createChartData(cumulativeDividend);
    }

    /**
     * 累計額の配列にして返す
     *
     * @param monthlyDividend 月別配当
     * @return 累計配当
     */
    BigDecimal[] getCumulativeDividend(BigDecimal[] monthlyDividend) {
        BigDecimal[] cumulativeDividend = Arrays.copyOf(monthlyDividend, monthlyDividend.length);

        for (int i = 1; i < cumulativeDividend.length; i++) {
            cumulativeDividend[i] = cumulativeDividend[i].add(cumulativeDividend[i - 1]);
        }
        return cumulativeDividend;
    }

}
