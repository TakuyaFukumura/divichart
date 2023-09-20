package jp.ne.divichart.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BarChartService extends BasicChartService {

    /**
     * グラフ描画用に、指定年の月別配当データを取得する
     *
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String getChartData(String year) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(year);
        return createChartData(monthlyDividend);
    }

}
