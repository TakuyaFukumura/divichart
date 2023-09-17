package com.example.divichart.service;

import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

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

    /*
     * 月別配当金額合計額を取得する
     */
    private BigDecimal[] getMonthlyDividend(String year) {
        BigDecimal[] monthlyDividend = new BigDecimal[12];

        for (int i = 0; i < 12; i++) {

            String startMonth = String.format("%02d", i+1);

            Date startDate = Date.valueOf(year + "-" + startMonth + "-01");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            calendar.add(Calendar.MONTH, 1); // 次月にする
            calendar.add(Calendar.DATE, -1); // 1引いて月末にする

            long timeInMilliSeconds = calendar.getTime().getTime();
            Date endDate = new Date(timeInMilliSeconds);

            BigDecimal dividendSum = repository.getDividendSum(startDate, endDate);
            monthlyDividend[i] = dividendSum;
        }
        return monthlyDividend;
    }

    /**
     * 累計額の配列にして返す
     * @param monthlyDividend 月別配当
     * @return cumulativeDividend 累計配当
     */
    private BigDecimal[] getCumulativeDividend(BigDecimal[] monthlyDividend) {
        BigDecimal[] cumulativeDividend = new BigDecimal[12];
        BigDecimal sum = new BigDecimal("0");

        for (int i = 0; i < monthlyDividend.length; i++) {
            sum = sum.add(monthlyDividend[i]);
            cumulativeDividend[i] = sum;
        }

        return cumulativeDividend;
    }

    /**
     * 受け取ったデータをコンマ区切りの文字列に合成して返す
     * @param cumulativeDividend 合成したいデータ
     * @return 合成した文字列 例）"1,2,3,4,5"
     */
    private String createChartData(BigDecimal[] cumulativeDividend) {
        StringBuilder chartData = new StringBuilder();
        chartData.append(cumulativeDividend[0].toString());
        for (int i = 1; i < cumulativeDividend.length; i++) {
            chartData.append(",");
            chartData.append(cumulativeDividend[i].toString());
        }
        return chartData.toString();
    }

}
