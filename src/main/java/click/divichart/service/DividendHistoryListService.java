package click.divichart.service;

import click.divichart.bean.entity.DividendHistory;
import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

@Slf4j
@Service
public class DividendHistoryListService {

    private final DividendHistoryRepository repository;

    @Autowired
    public DividendHistoryListService(DividendHistoryRepository dividendHistoryRepository) {
        this.repository = dividendHistoryRepository;
    }

    /**
     * 対象ページの配当履歴一覧を取得する
     *
     * @param pageable 取得対象ページ情報
     * @return 対象ページの配当履歴一覧
     */
    public Page<DividendHistory> getDividendHistoryPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 配当履歴を登録する
     *
     * @param tickerSymbol   ティッカーシンボル
     * @param amountReceived 受取金額
     * @param receiptDate    受取日
     */
    public void insertDividendHistory(String tickerSymbol, BigDecimal amountReceived,
                                      Date receiptDate, String username) {
        DividendHistory dividendHistory = new DividendHistory(tickerSymbol, amountReceived, receiptDate, username);
        repository.save(dividendHistory);
    }

    /**
     * CSVファイルを読み取って配当履歴をinsertする
     *
     * @param csvFile CSVファイル内容
     */
    public void bulkInsert(MultipartFile csvFile, String username) {
        try {
            List<DividendHistory> dividendHistoryList = parseCsvFile(csvFile, username);
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
    private List<DividendHistory> parseCsvFile(MultipartFile csvFile, String username) throws IOException {
        List<DividendHistory> dividendHistoryList = new ArrayList<>();

        try (InputStream inputStream = csvFile.getInputStream();
             Reader reader = new InputStreamReader(inputStream, "SJIS");
             CSVParser csvParser = CSVFormat.EXCEL.withHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                String stockCode = csvRecord.get("銘柄コード");
                if (isTickerSymbol(stockCode)) {
                    BigDecimal amountReceived = new BigDecimal(csvRecord.get("受取金額[円/現地通貨]"));
                    String rawDate = csvRecord.get("入金日");
                    String formattedDate = rawDate.replace("/", "-");
                    Date receiptDate = Date.valueOf(formattedDate);
                    DividendHistory dividendHistory = new DividendHistory(
                            stockCode,
                            amountReceived,
                            receiptDate,
                            username
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
