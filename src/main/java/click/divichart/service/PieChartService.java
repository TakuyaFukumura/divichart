package click.divichart.service;

import click.divichart.bean.dto.PieChartDto;
import click.divichart.repository.DividendHistoryRepository;
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
     *
     * @param year データ作成対象年
     * @return グラフ描画用文字列配列
     */
    public PieChartDto getChartData(String year) {
        LocalDate startDate = LocalDate.parse(year + "-01-01");
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<Object[]> dividendSummaryList = repository.getDividendTotalForStock(startDate, endDate);
        return createChartData(dividendSummaryList);
    }

    /**
     * 配当情報からグラフ描画用のデータを生成する
     *
     * @param dividendSummaryList 配当情報
     * @return グラフ描画用文字列配列
     */
    PieChartDto createChartData(List<Object[]> dividendSummaryList) {
        StringJoiner tickerSymbolData = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner amountReceivedData = new StringJoiner(",");

        for (Object[] dividendSummary : dividendSummaryList) {
            String tickerSymbol = (String) dividendSummary[0];
            BigDecimal amountReceived = (BigDecimal) dividendSummary[1];

            tickerSymbolData.add(tickerSymbol);
            amountReceivedData.add(amountReceived.toString());
        }

        PieChartDto pieChartDto = new PieChartDto(
                tickerSymbolData.toString(),
                amountReceivedData.toString()
        );

        return pieChartDto;
    }

}
