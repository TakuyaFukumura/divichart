package com.example.divichart.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PieChartService extends BasicChartService {

    /**
     * グラフ描画用に、指定年の配当割合データを取得する
     * TODO:見直す
     * @param year データ作成対象年
     * @return グラフ描画用文字列
     */
    public String getChartData(String year) {
        // 銘柄情報が必要
        // テーブル編集しないといけなさそう

        return null;
    }

}
