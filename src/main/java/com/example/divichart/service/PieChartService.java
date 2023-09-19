package com.example.divichart.service;

import com.example.divichart.dto.DividendDto;
import com.example.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Service
public class PieChartService extends BasicChartService {

    @Autowired
    DividendHistoryRepository repository;

    /**
     * グラフ描画用に、指定年の配当割合データを取得する
     * TODO:見直す
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String[] getChartData(String year) {
        // 必要なもの：グラフ描画用データ、銘柄データ
        LocalDate startDate = LocalDate.parse(year + "-01-01");
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<Object[]> dividendTotalForStock = repository.getDividendTotalForStock(startDate, endDate);
        return createChartData(dividendTotalForStock);
    }

    private String[] createChartData(List<Object[]> dividendTotalForStock) {
        String[] chartData = new String[2];
        StringJoiner AmountReceivedData = new StringJoiner(",");
        for (Object[] dividendDto : dividendTotalForStock) {
            BigDecimal tmp = (BigDecimal) dividendDto[1];
            AmountReceivedData.add(tmp.toString());
        }

        String[] monthlyDividend = new String[dividendTotalForStock.size()];
        for(int i = 0; i < monthlyDividend.length; ++i) {
            Object[] tmp = dividendTotalForStock.get(i);
            monthlyDividend[i] = (String) tmp[0];

        }
        String result = "\"";
        if(monthlyDividend.length != 0){
            result += monthlyDividend[0];
            for(int i = 1; i < monthlyDividend.length; i++) {
                result += "\",\"";
                result += monthlyDividend[i];
            }
        }
        result += "\"";

        chartData[0] = result;
        chartData[1] = AmountReceivedData.toString();
        return chartData;
    }

}
