package click.divichart.service;

import click.divichart.bean.dto.DividendTotalForStockDto;
import click.divichart.bean.dto.PieChartDto;
import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
        List<DividendTotalForStockDto> dividendTotalForStockDtoList = dataFormatting(dividendSummaryList);

        return createChartData(dividendTotalForStockDtoList);
    }

    List<DividendTotalForStockDto> dataFormatting(List<Object[]> dividendSummaryList) {

        List<DividendTotalForStockDto> dividendTotalForStockDtoList = new ArrayList<>();
        DividendTotalForStockDto other = new DividendTotalForStockDto(
                "その他",
                BigDecimal.ZERO
        );

        for (Object[] dividendSummary : dividendSummaryList) {
            DividendTotalForStockDto dividendTotalForStockDto = new DividendTotalForStockDto(
                    (String) dividendSummary[0],
                    (BigDecimal) dividendSummary[1]
            );
            if (dividendTotalForStockDtoList.size() < 15) {
                dividendTotalForStockDtoList.add(dividendTotalForStockDto);
            } else {
                other.setAmountReceived(
                        other.getAmountReceived().add(
                                dividendTotalForStockDto.getAmountReceived()
                        )
                );
            }
        }
        if (other.getAmountReceived().compareTo(BigDecimal.ZERO) != 0) {
            dividendTotalForStockDtoList.add(other);
        }
        return dividendTotalForStockDtoList;
    }

    /**
     * 配当情報からグラフ描画用のデータを生成する
     *
     * @param dividendTotalForStockDtoList 配当情報
     * @return グラフ描画用文字列配列
     */
    PieChartDto createChartData(List<DividendTotalForStockDto> dividendTotalForStockDtoList) {
        StringJoiner tickerSymbolData = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner amountReceivedData = new StringJoiner(",");

        for (DividendTotalForStockDto dividendTotalForStockDto : dividendTotalForStockDtoList) {
            String tickerSymbol = dividendTotalForStockDto.getTickerSymbol();
            BigDecimal amountReceived = dividendTotalForStockDto.getAmountReceived();

            tickerSymbolData.add(tickerSymbol);
            amountReceivedData.add(amountReceived.toString());
        }

        return new PieChartDto(
                tickerSymbolData.toString(),
                amountReceivedData.toString()
        );
    }

}
