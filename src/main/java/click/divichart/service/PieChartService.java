package click.divichart.service;

import click.divichart.bean.DividendSummaryBean;
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
     * @param targetYear データ作成対象年
     * @return グラフ描画用文字列配列
     */
    public PieChartDto getChartData(String targetYear) {
        LocalDate startDate = LocalDate.parse(targetYear + "-01-01");
        LocalDate endDate = startDate.plusYears(1).minusDays(1);

        List<Object[]> dividendSummaryList = repository.getDividendsForEachStock(startDate, endDate);
        List<DividendSummaryBean> dividendSummaryBeanList = consolidateSmallValues(dividendSummaryList);

        return createChartData(dividendSummaryBeanList);
    }

    /**
     * 配当の集計情報を整理して、小さいデータはその他にまとめる
     *
     * @param dividendSummaryList 配当の集計情報
     * @return 整理された配当の集計情報
     */
    List<DividendSummaryBean> consolidateSmallValues(List<Object[]> dividendSummaryList) {
        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>();
        DividendSummaryBean others = new DividendSummaryBean("その他", BigDecimal.ZERO);

        for (Object[] dividendSummary : dividendSummaryList) {
            String tickerSymbol = (String) dividendSummary[0];
            BigDecimal amountReceived = (BigDecimal) dividendSummary[1];

            DividendSummaryBean dividendSummaryBean = new DividendSummaryBean(tickerSymbol, amountReceived);

            if (dividendSummaryBeanList.size() < MAX_DISPLAYED_STOCKS) {
                dividendSummaryBeanList.add(dividendSummaryBean);
            } else {
                addToOthers(others, dividendSummaryBean);
            }
        }
        if (others.getAmountReceived().compareTo(BigDecimal.ZERO) != 0) {
            dividendSummaryBeanList.add(others);
        }
        return dividendSummaryBeanList;
    }

    /**
     * その他の配当情報に加算
     *
     * @param others             その他の配当情報
     * @param dividendSummaryBean 加算したい配当情報
     */
    private void addToOthers(DividendSummaryBean others, DividendSummaryBean dividendSummaryBean) {
        BigDecimal currentAmountReceived = others.getAmountReceived();
        BigDecimal newAmountReceived = currentAmountReceived.add(dividendSummaryBean.getAmountReceived());
        others.setAmountReceived(newAmountReceived);
    }

    /**
     * 配当情報からグラフ描画用のデータを生成する
     *
     * @param dividendSummaryBeanList 配当情報
     * @return グラフ描画用文字列
     */
    PieChartDto createChartData(List<DividendSummaryBean> dividendSummaryBeanList) {
        StringJoiner labels = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner chartData = new StringJoiner(",");

        for (DividendSummaryBean dividendSummaryBean : dividendSummaryBeanList) {
            String tickerSymbol = dividendSummaryBean.getTickerSymbol();
            BigDecimal amountReceived = dividendSummaryBean.getAmountReceived();

            labels.add(tickerSymbol);
            chartData.add(amountReceived.toString());
        }

        return new PieChartDto(
                labels.toString(),
                chartData.toString()
        );
    }

}
