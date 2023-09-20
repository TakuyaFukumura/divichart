package com.example.divichart.service;

import com.example.divichart.entity.DividendHistory;
import com.example.divichart.repository.DividendHistoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {

    private static final Logger log = LoggerFactory.getLogger(ListService.class);

    @Autowired
    DividendHistoryRepository repository;

    public List<DividendHistory> getAllDividendHistory() {
        return repository.findAll();
    }

    public void insertDividendHistory(String tickerSymbol, BigDecimal amountReceived, Date receiptDate) {
        DividendHistory dividendHistory = new DividendHistory(tickerSymbol, amountReceived, receiptDate);
        repository.save(dividendHistory);
    }

    /**
     * CSVファイルを読み取って配当履歴をinsertする
     *
     * @param csvFile CSVファイル内容
     */
    public void csvInsert(MultipartFile csvFile) {
        try {
            List<DividendHistory> dividendHistoryList = parseCsvFile(csvFile);
            repository.saveAll(dividendHistoryList);
        } catch (IOException e) {
            log.error("CSVファイルの読み込みまたはDBへの保存中にエラーが発生しました。", e);
        }
    }

    /**
     *
     * @param csvFile アップロードされた配当CSV
     * @return CSVファイルを読み込んで取得した配当履歴リスト
     * @throws IOException 入出力例外
     */
    private List<DividendHistory> parseCsvFile(MultipartFile csvFile) throws IOException {
        List<DividendHistory> dividendHistoryList = new ArrayList<>();

        try (InputStream stream = csvFile.getInputStream();
             Reader reader = new InputStreamReader(stream, "SJIS");
             CSVParser parse = CSVFormat.EXCEL.withHeader().parse(reader)) {

            for (CSVRecord record : parse) {
                String tickerSymbol = record.get("銘柄コード");
                if (!tickerSymbol.isEmpty() && tickerSymbol.matches("^[A-Z]*$")) {
                    BigDecimal amountReceived = new BigDecimal(record.get("受取金額[円/現地通貨]"));
                    String rowDate = record.get("入金日");
                    String sqlDate = rowDate.replace("/", "-");
                    Date receiptDate = Date.valueOf(sqlDate);
                    DividendHistory dividendHistory = new DividendHistory(
                            tickerSymbol,
                            amountReceived,
                            receiptDate
                    );
                    dividendHistoryList.add(dividendHistory);
                }
            }
        }
        return dividendHistoryList;
    }

    /**
     * 銘柄コードが大文字の英字のみで構成されているかを検証します。
     *
     * @param tickerSymbol 銘柄コード
     * @return 大文字英字のみの銘柄コードである場合はtrue、それ以外の場合はfalse
     */
    private boolean isValidTickerSymbol(String tickerSymbol) {
        return !tickerSymbol.isEmpty() && tickerSymbol.matches("^[A-Z]*$");
    }

}
