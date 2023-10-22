package click.divichart.service;

import click.divichart.bean.dto.DividendSummaryDto;
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

    private static final int MAX_DISPLAYED_STOCKS = 15;

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
        List<DividendSummaryDto> dividendSummaryDtoList = dataFormatting(dividendSummaryList);

        return createChartData(dividendSummaryDtoList);
    }

    List<DividendSummaryDto> dataFormatting(List<Object[]> dividendSummaryList) {

        List<DividendSummaryDto> dividendSummaryDtoList = new ArrayList<>();
        DividendSummaryDto other = new DividendSummaryDto(
                "その他",
                BigDecimal.ZERO
        );

        for (Object[] dividendSummary : dividendSummaryList) {
            DividendSummaryDto dividendSummaryDto = new DividendSummaryDto(
                    (String) dividendSummary[0],
                    (BigDecimal) dividendSummary[1]
            );
            if (dividendSummaryDtoList.size() < MAX_DISPLAYED_STOCKS) {
                dividendSummaryDtoList.add(dividendSummaryDto);
            } else {
                other.setAmountReceived(
                        other.getAmountReceived().add(
                                dividendSummaryDto.getAmountReceived()
                        )
                );
            }
        }
        if (other.getAmountReceived().compareTo(BigDecimal.ZERO) != 0) {
            dividendSummaryDtoList.add(other);
        }
        return dividendSummaryDtoList;
    }

    /**
     * 配当情報からグラフ描画用のデータを生成する
     *
     * @param dividendSummaryDtoList 配当情報
     * @return グラフ描画用文字列配列
     */
    PieChartDto createChartData(List<DividendSummaryDto> dividendSummaryDtoList) {
        StringJoiner tickerSymbolData = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner amountReceivedData = new StringJoiner(",");

        for (DividendSummaryDto dividendSummaryDto : dividendSummaryDtoList) {
            String tickerSymbol = dividendSummaryDto.getTickerSymbol();
            BigDecimal amountReceived = dividendSummaryDto.getAmountReceived();

            tickerSymbolData.add(tickerSymbol);
            amountReceivedData.add(amountReceived.toString());
        }

        return new PieChartDto(
                tickerSymbolData.toString(),
                amountReceivedData.toString()
        );
    }

}
