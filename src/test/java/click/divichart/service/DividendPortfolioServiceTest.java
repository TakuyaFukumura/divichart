package click.divichart.service;

import click.divichart.bean.DividendSummaryBean;
import click.divichart.bean.dto.DividendPortfolioDto;
import click.divichart.bean.dto.DividendSumsByStockProjection;
import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DividendPortfolioServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private DividendPortfolioService dividendPortfolioService;

    @BeforeEach
    void setUp() {
        dividendPortfolioService = new DividendPortfolioService(repository);
    }

//    @Test
//    void testConsolidateSmallValuesWithMocks() {
//        List<DividendSumsByStockProjection> dividendSummaryList = new ArrayList<>();
//
//        for (int i = 1; i <= 20; i++) {
//            DividendSumsByStockProjection mock = mock(DividendSumsByStockProjection.class);
//            when(mock.getTickerSymbol()).thenReturn("STOCK" + i);
//            when(mock.getAmountReceived()).thenReturn(BigDecimal.valueOf(i * 10));
//            dividendSummaryList.add(mock);
//        }
//
//        List<DividendSummaryBean> result = dividendPortfolioService.consolidateSmallValues(dividendSummaryList);
//
//        assertNotNull(result);
//        assertEquals(16, result.size());
//
//        BigDecimal othersAmount = result.stream()
//                .filter(bean -> "その他".equals(bean.getTickerSymbol()))
//                .map(DividendSummaryBean::getAmountReceived)
//                .findFirst()
//                .orElse(BigDecimal.ZERO);
//
//        assertEquals(BigDecimal.valueOf(160 + 170 + 180 + 190 + 200), othersAmount); // 16〜20番目の合計
//    }

//    @Test
//    void testCreateChartDataWithValidData() {
//        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>();
//        dividendSummaryBeanList.add(new DividendSummaryBean("AAPL", BigDecimal.valueOf(100.01)));
//        dividendSummaryBeanList.add(new DividendSummaryBean("GOOG", BigDecimal.valueOf(50.01)));
//
//        DividendPortfolioDto chartData = dividendPortfolioService.convertChartData(dividendSummaryBeanList, BigDecimal.valueOf(150.02));
//
//        assertNotNull(chartData);
//
//        // 期待される結果を確認します
//        assertEquals("\"AAPL 66.66%\",\"GOOG 33.34%\"", chartData.getLabels());
//        assertEquals("100.01,50.01", chartData.getDividendPortfolioData());
//    }
//
//    @Test
//    void testCreateChartDataWithEmptyList() {
//        List<DividendSummaryBean> emptyList = new ArrayList<>();
//
//        DividendPortfolioDto chartData = dividendPortfolioService.convertChartData(emptyList, BigDecimal.valueOf(150.02));
//
//        assertNotNull(chartData);
//
//        // 空のリストが渡された場合、空の文字列が返されることを確認します
//        assertEquals("\"\"", chartData.getLabels());
//        assertEquals("", chartData.getDividendPortfolioData());
//    }

}
