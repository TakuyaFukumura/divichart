package click.divichart.service;

import click.divichart.bean.DividendSummaryBean;
import click.divichart.bean.dto.DividendPortfolioDto;
import click.divichart.bean.dto.DividendSumsByStockProjection;
import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
public class DividendPortfolioService extends DividendService {

    private static final int MAX_DISPLAYED_STOCKS = 15;

    public DividendPortfolioService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * 配当ポートフォリオデータを生成します。
     *
     * @param targetYear データ作成対象年
     * @param username ユーザ名
     * @return 最大15銘柄＋その他で構成されるデータ
     * @throws IllegalArgumentException 無効な入力の場合
     * @see DividendPortfolioDto 戻り値の形式詳細
     */
    public List<DividendSummaryBean> getChartData(int targetYear, String username) {
        LocalDate startDate = LocalDate.of(targetYear, 1, 1);
        LocalDate endDate = LocalDate.of(targetYear, 12, 31);

        List<DividendSumsByStockProjection> dividendSummaryList = repository.findDividendSumsByStock(startDate, endDate, username);

        List<DividendSummaryBean> mainItems = dividendSummaryList.stream()
                .limit(MAX_DISPLAYED_STOCKS)
                .map(this::toSummaryBean)
                .toList();

        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>(mainItems);

        BigDecimal othersSum = dividendSummaryList.stream()
                .skip(MAX_DISPLAYED_STOCKS)
                .map(DividendSumsByStockProjection::getAmountReceived)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (othersSum.compareTo(BigDecimal.ZERO) > 0) {
            dividendSummaryBeanList.add(new DividendSummaryBean("その他", othersSum));
        }
        return dividendSummaryBeanList;
    }

    private DividendSummaryBean toSummaryBean(DividendSumsByStockProjection projection) {
        return new DividendSummaryBean(projection.getTickerSymbol(), projection.getAmountReceived());
    }

    public DividendPortfolioDto createChartData(int targetYear, String username, List<DividendSummaryBean> dividendSummaryBeanList) {
        BigDecimal dividendSum = getDividendSumForYear(targetYear, username);

        List<String> labelsList = dividendSummaryBeanList.stream()
                .map(bean -> createLabel(
                        bean.getTickerSymbol(),
                        bean.getAmountReceived(),
                        dividendSum
                ))
                .toList();

        List<String> dataList = dividendSummaryBeanList.stream()
                .map(bean -> bean.getAmountReceived().toString())
                .toList();

        String labels = labelsList.isEmpty() ? "\"\"" : "\"" + String.join("\",\"", labelsList) + "\"";
        String chartData = String.join(",", dataList);

        return new DividendPortfolioDto(labels, chartData);
    }

    private BigDecimal getDividendSumForYear(int targetYear, String username) {
        LocalDate startDate = LocalDate.of(targetYear, 1, 1);
        LocalDate endDate = LocalDate.of(targetYear, 12, 31);
        return repository.getDividendSum(startDate, endDate, username);
    }

    /**
     * チャートのラベルを作成する
     *
     * @param tickerSymbol   ティッカー
     * @param amountReceived 配当受取額
     * @param dividendSum    配当合計額
     * @return チャートのラベル
     */
    String createLabel(String tickerSymbol, BigDecimal amountReceived, BigDecimal dividendSum) {

        BigDecimal percentageOfPortfolio;

        // 配当合計額がゼロの場合、ゼロ除算を回避する
        if (BigDecimal.ZERO.compareTo(dividendSum) == 0) {
            percentageOfPortfolio = BigDecimal.ZERO;
        } else {
            // 配当受取額 × 100 ÷ 配当合計額
            percentageOfPortfolio = amountReceived.multiply(BigDecimal.valueOf(100))
                    .divide(dividendSum, RoundingMode.HALF_UP);
        }

        return tickerSymbol + " " + percentageOfPortfolio + "%";
    }
}
