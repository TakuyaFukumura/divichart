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
     * @param username   ユーザ名
     * @return 最大15銘柄＋その他で構成されるデータ
     * @throws IllegalArgumentException 無効な入力の場合
     * @see DividendPortfolioDto 戻り値の形式詳細
     */
    public List<DividendSummaryBean> getDividendPortfolioData(int targetYear, String username) {
        LocalDate startDate = LocalDate.of(targetYear, 1, 1);
        LocalDate endDate = LocalDate.of(targetYear, 12, 31);

        List<DividendSumsByStockProjection> dividendSumsByStocks =
                repository.findDividendSumsByStock(startDate, endDate, username);

        List<DividendSummaryBean> mainItems = dividendSumsByStocks.stream()
                .limit(MAX_DISPLAYED_STOCKS)
                .map(this::toSummaryBean)
                .toList();

        List<DividendSummaryBean> dividendSummaryBeans = new ArrayList<>(mainItems);

        BigDecimal othersSum = dividendSumsByStocks.stream()
                .skip(MAX_DISPLAYED_STOCKS)
                .map(DividendSumsByStockProjection::getAmountReceived)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (othersSum.compareTo(BigDecimal.ZERO) > 0) {
            dividendSummaryBeans.add(new DividendSummaryBean("その他", othersSum));
        }
        return dividendSummaryBeans;
    }

    private DividendSummaryBean toSummaryBean(DividendSumsByStockProjection projection) {
        return new DividendSummaryBean(projection.getTickerSymbol(), projection.getAmountReceived());
    }

    public String getChartData(List<DividendSummaryBean> dividendSummaryBeans) {
        List<String> amountReceivedData = dividendSummaryBeans.stream()
                .map(bean -> bean.getAmountReceived().toString())
                .toList();
        return String.join(",", amountReceivedData);
    }

    public String getDividendPortfolioLabels(BigDecimal dividendSum, List<DividendSummaryBean> dividendSummaryBeans) {
        List<String> labelParts = dividendSummaryBeans.stream()
                .map(bean -> createLabelPart(
                        bean.getTickerSymbol(),
                        bean.getAmountReceived(),
                        dividendSum
                ))
                .toList();
        return labelParts.isEmpty() ? "\"\"" : "\"" + String.join("\",\"", labelParts) + "\"";
    }

    /**
     * チャートのラベルを作成する
     *
     * @param tickerSymbol   ティッカー
     * @param amountReceived 配当受取額
     * @param dividendSum    配当合計額
     * @return チャートのラベル
     */
    String createLabelPart(String tickerSymbol, BigDecimal amountReceived, BigDecimal dividendSum) {
        BigDecimal percentageOfPortfolio = dividendSum.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : amountReceived.multiply(BigDecimal.valueOf(100)).divide(dividendSum, 2, RoundingMode.HALF_UP);
        return String.format("%s %.2f%%", tickerSymbol, percentageOfPortfolio);
    }
}
