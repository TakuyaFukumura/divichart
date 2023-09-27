package jp.ne.divichart.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LineChartService extends BasicChartService {

    /**
     * グラフ描画用に、指定年の1月～12月までの配当累計を計算する
     *
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String getChartData(String year) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(year);
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
        int arrayLength = monthlyDividend.length;
        BigDecimal[] cumulativeDividend = new BigDecimal[arrayLength];
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < arrayLength; i++) {
            sum = sum.add(monthlyDividend[i]);
            cumulativeDividend[i] = sum;
        }
        return cumulativeDividend;
    }

}
