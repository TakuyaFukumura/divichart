package com.example.divichart.service;

import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

@Service
public class LineChartService {

    @Autowired
    DividendHistoryRepository repository;

    /**
     * グラフ描画用に、指定年の1月～12月までの配当累計を計算する
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String getChartData(String year) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(year);
        BigDecimal[] cumulativeDividend = getCumulativeDividend(monthlyDividend);
        return createChartData(cumulativeDividend);
    }

    /**
     * 月別配当金額を取得する
     * @param year 対象年
     * @return 月別配当配列
     */
    private BigDecimal[] getMonthlyDividend(String year) {
        BigDecimal[] monthlyDividend = new BigDecimal[12];

        for (int i = 0; i < 12; i++) {
            int month = i + 1;
            String formattedMonth = String.format("%02d", month);

            LocalDate startDate = LocalDate.parse(year + "-" + formattedMonth + "-01");
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate);
            monthlyDividend[i] = dividendSum;
        }
        return monthlyDividend;
    }

    /**
     * 累計額の配列にして返す
     * @param monthlyDividend 月別配当
     * @return 累計配当
     */
    private BigDecimal[] getCumulativeDividend(BigDecimal[] monthlyDividend) {
        int arrayLength = monthlyDividend.length;
        BigDecimal[] cumulativeDividend = new BigDecimal[arrayLength];
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < arrayLength; i++) {
            sum = sum.add(monthlyDividend[i]);
            cumulativeDividend[i] = sum;
        }
        return cumulativeDividend;
    }

    /**
     * 受け取ったデータをグラフ描画用に合成する
     * @param cumulativeDividend 合成したいデータ配列
     * @return 合成した文字列 例）"1,2,3,4,5"
     */
    private String createChartData(BigDecimal[] cumulativeDividend) {
        StringJoiner chartData = new StringJoiner(",");
        for (BigDecimal dividend : cumulativeDividend) {
            chartData.add(dividend.toString());
        }
        return chartData.toString();
    }

}
