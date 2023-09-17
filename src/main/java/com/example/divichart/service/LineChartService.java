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

    /*
     * @return result グラフ描画用データ
     */
    public String getChartData(String year) {

        String chartData = "";
        // 該当する年のデータを取得。月別の合計(sum)で取りたい
        BigDecimal[] monthlyDividendSum = getMonthlyDividendSum(year);
        // 累計の配列に作り直す
        BigDecimal[] cumulativeDividend = getCumulativeDividend(monthlyDividendSum);
        // コンマで連結する
        return chartData;
    }

    /*
     * 月別配当金額合計を取得する
     */
    public BigDecimal[] getMonthlyDividendSum(String year) {
        BigDecimal[] monthlyDividendSum = new BigDecimal[12];

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
            monthlyDividendSum[i] = dividendSum;
        }
        return monthlyDividendSum;
    }

    /**
     * 累計額の配列にして返す
     * @param monthlyDividendSum 月別配当
     * @return cumulativeDividend 累計配当
     */
    private BigDecimal[] getCumulativeDividend(BigDecimal[] monthlyDividendSum) {
        BigDecimal[] cumulativeDividend = new BigDecimal[12];
        BigDecimal sum = new BigDecimal("0");

        for (int i = 0; i < 12; i++) {
            sum = sum.add(monthlyDividendSum[i]);
            cumulativeDividend[i] = sum;
        }

        return cumulativeDividend;
    }

}
