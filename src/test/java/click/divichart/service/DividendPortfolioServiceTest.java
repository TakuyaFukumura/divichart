package click.divichart.service;

import click.divichart.bean.DividendSummaryBean;
import click.divichart.bean.dto.DividendPortfolioDto;
import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DividendPortfolioServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private DividendPortfolioService dividendPortfolioService;

    @BeforeEach
    void setUp() {
        dividendPortfolioService = new DividendPortfolioService(repository);
    }

    @Test
    void testCreateChartDataWithValidData() {
        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>();
        dividendSummaryBeanList.add(new DividendSummaryBean("AAPL", BigDecimal.valueOf(100.01)));
        dividendSummaryBeanList.add(new DividendSummaryBean("GOOG", BigDecimal.valueOf(50.01)));

        DividendPortfolioDto chartData = dividendPortfolioService.createChartData(dividendSummaryBeanList, BigDecimal.valueOf(150.02));

        assertNotNull(chartData);

        // 期待される結果を確認します
        assertEquals("\"AAPL 66.66%\",\"GOOG 33.34%\"", chartData.getLabels());
        assertEquals("100.01,50.01", chartData.getChartData());
    }

    @Test
    void testCreateChartDataWithEmptyList() {
        List<DividendSummaryBean> emptyList = new ArrayList<>();

        DividendPortfolioDto chartData = dividendPortfolioService.createChartData(emptyList, BigDecimal.valueOf(150.02));

        assertNotNull(chartData);

        // 空のリストが渡された場合、空の文字列が返されることを確認します
        assertEquals("\"\"", chartData.getLabels());
        assertEquals("", chartData.getChartData());
    }

}
