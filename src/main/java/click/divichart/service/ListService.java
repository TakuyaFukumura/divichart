package click.divichart.service;

import click.divichart.entity.DividendHistory;
import click.divichart.repository.DividendHistoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DividendHistoryRepository repository;

    public Page<DividendHistory> getDividendHistory(Pageable pageable) {
        return repository.findAll(pageable);
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
     * CSVファイルを解析し、配当履歴リストを取得します。
     *
     * @param csvFile アップロードされた配当CSV
     * @return CSVファイルを読み込んで取得した配当履歴リスト
     * @throws IOException 入出力例外が発生した場合
     */
    private List<DividendHistory> parseCsvFile(MultipartFile csvFile) throws IOException {
        List<DividendHistory> dividendHistoryList = new ArrayList<>();

        try (InputStream inputStream = csvFile.getInputStream();
             Reader reader = new InputStreamReader(inputStream, "SJIS");
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(reader)) {

            for (CSVRecord record : csvParser) {
                String stockCode = record.get("銘柄コード");
                if (isTickerSymbol(stockCode)) {
                    BigDecimal amountReceived = new BigDecimal(record.get("受取金額[円/現地通貨]"));
                    String rawDate = record.get("入金日");
                    String formattedDate = rawDate.replace("/", "-");
                    Date receiptDate = Date.valueOf(formattedDate);
                    DividendHistory dividendHistory = new DividendHistory(
                            stockCode,
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
     * 銘柄コードがティッカーシンボルであるかを検証します。
     *
     * @param stockCode 銘柄コード
     * @return 空でなく大文字英字のみである場合はtrue、それ以外の場合はfalse
     */
    boolean isTickerSymbol(String stockCode) {
        return !stockCode.isEmpty() && stockCode.matches("^[A-Z]*$");
    }

}
