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
    public DividendPortfolioDto getChartData(int targetYear, String username) {
        LocalDate startDate = LocalDate.of(targetYear, 1, 1);
        LocalDate endDate = LocalDate.of(targetYear, 12, 31);

        List<DividendSumsByStockProjection> dividendSummaryList = repository.findDividendSumsByStock(startDate, endDate, username);
        List<DividendSummaryBean> dividendSummaryBeanList = consolidateSmallValues(dividendSummaryList);

        BigDecimal dividendSum = repository.getDividendSum(startDate, endDate, username);

        return createChartData(dividendSummaryBeanList, dividendSum);
    }

    /**
     * 配当の集計情報を整理して、小さいデータはその他にまとめる
     *
     * @param dividendSummaryList 配当の集計情報
     * @return 整理された配当の集計情報（ティッカー名と金額）
     */
    List<DividendSummaryBean> consolidateSmallValues(List<DividendSumsByStockProjection> dividendSummaryList) {
        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>();
        DividendSummaryBean others = new DividendSummaryBean("その他", BigDecimal.ZERO);

        for (DividendSumsByStockProjection dividendSummary : dividendSummaryList) {
            String tickerSymbol = dividendSummary.getTickerSymbol();
            BigDecimal amountReceived = dividendSummary.getAmountReceived();

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
     * @param others              その他の配当情報
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
     * @param dividendSummaryBeanList 配当情報リスト
     * @param dividendSum             配当合計額
     * @return グラフ描画用文字列
     */
    DividendPortfolioDto createChartData(List<DividendSummaryBean> dividendSummaryBeanList, BigDecimal dividendSum) {
        StringJoiner labels = new StringJoiner("\",\"", "\"", "\"");
        StringJoiner chartData = new StringJoiner(",");

        for (DividendSummaryBean dividendSummaryBean : dividendSummaryBeanList) {
            String tickerSymbol = dividendSummaryBean.getTickerSymbol();
            BigDecimal amountReceived = dividendSummaryBean.getAmountReceived();

            String label = createLabel(tickerSymbol, amountReceived, dividendSum);

            labels.add(label);
            chartData.add(amountReceived.toString());
        }

        return new DividendPortfolioDto(
                labels.toString(),
                chartData.toString()
        );
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
