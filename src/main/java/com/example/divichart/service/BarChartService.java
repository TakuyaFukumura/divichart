package com.example.divichart.service;

import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

@Service
public class BarChartService extends BasicChartService{

    @Autowired
    DividendHistoryRepository repository;

    /**
     * グラフ描画用に、指定年の月別配当データを取得する
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String getChartData(String year) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(year);
        return createChartData(monthlyDividend);
    }

}
