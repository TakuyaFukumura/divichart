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

    public void insertDividendHistory(BigDecimal amountReceived, Date receiptDate) {
        DividendHistory dividendHistory = new DividendHistory(amountReceived, receiptDate);
        repository.save(dividendHistory);
    }

    /**
     * CSVファイルを読み取って配当履歴をinsertする
     *
     * @param csvFile CSVファイル内容
     */
    public void csvInsert(MultipartFile csvFile) {

        List<DividendHistory> dividendHistoryList = new ArrayList<>();

        try (InputStream stream = csvFile.getInputStream();
             Reader reader = new InputStreamReader(stream, "SJIS");
             BufferedReader buf = new BufferedReader(reader)) {

            CSVParser parse = CSVFormat.EXCEL.withHeader().parse(buf);
            List<CSVRecord> recordList = parse.getRecords();

            for (CSVRecord record : recordList) {
                if (!record.get("銘柄コード").isEmpty()) {
                    BigDecimal amountReceived = new BigDecimal(record.get("受取金額[円/現地通貨]"));
                    String rowDate = record.get("入金日");
                    String sqlDate = rowDate.replace("/", "-");
                    Date receiptDate = Date.valueOf(sqlDate);
                    DividendHistory dividendHistory = new DividendHistory(
                            amountReceived,
                            receiptDate
                    );
                    dividendHistoryList.add(dividendHistory);
                }
            }
            repository.saveAll(dividendHistoryList);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
