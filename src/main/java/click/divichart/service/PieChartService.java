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
        List<DividendSummaryDto> dividendSummaryDtoList = formatData(dividendSummaryList);

        return createChartData(dividendSummaryDtoList);
    }

    /**
     * 配当の集計情報を整形して、小さいデータはその他にまとめる
     *
     * @param dividendSummaryList 配当の集計情報
     * @return 整形された配当の集計情報
     */
    List<DividendSummaryDto> formatData(List<Object[]> dividendSummaryList) {
        List<DividendSummaryDto> dividendSummaryDtoList = new ArrayList<>();
        DividendSummaryDto others = new DividendSummaryDto("その他", BigDecimal.ZERO);

        for (Object[] dividendSummary : dividendSummaryList) {
            String stockName = (String) dividendSummary[0];
            BigDecimal amountReceived = (BigDecimal) dividendSummary[1];

            DividendSummaryDto dividendSummaryDto = new DividendSummaryDto(stockName, amountReceived);

            if (dividendSummaryDtoList.size() < MAX_DISPLAYED_STOCKS) {
                dividendSummaryDtoList.add(dividendSummaryDto);
            } else {
                addToOthers(others, dividendSummaryDto);
            }
        }
        if (others.getAmountReceived().compareTo(BigDecimal.ZERO) != 0) {
            dividendSummaryDtoList.add(others);
        }
        return dividendSummaryDtoList;
    }

    /***
     * その他の配当情報に加算
     * @param others その他の配当情報
     * @param dividendSummaryDto 加算したい配当情報
     */
    private void addToOthers(DividendSummaryDto others, DividendSummaryDto dividendSummaryDto) {
        BigDecimal currentAmountReceived = others.getAmountReceived();
        BigDecimal newAmountReceived = currentAmountReceived.add(dividendSummaryDto.getAmountReceived());
        others.setAmountReceived(newAmountReceived);
    }

    /**
     * 配当情報からグラフ描画用のデータを生成する
     *
     * @param dividendSummaryDtoList 配当情報
     * @return グラフ描画用文字列配列
     */
    PieChartDto createChartData(List<DividendSummaryDto> dividendSummaryDtoList) {
        StringJoiner labels = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner amountReceivedData = new StringJoiner(",");

        for (DividendSummaryDto dividendSummaryDto : dividendSummaryDtoList) {
            String tickerSymbol = dividendSummaryDto.getTickerSymbol();
            BigDecimal amountReceived = dividendSummaryDto.getAmountReceived();

            labels.add(tickerSymbol);
            amountReceivedData.add(amountReceived.toString());
        }

        return new PieChartDto(
                labels.toString(),
                amountReceivedData.toString()
        );
    }

}
