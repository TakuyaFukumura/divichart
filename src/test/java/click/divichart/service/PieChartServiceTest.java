package click.divichart.service;

import click.divichart.bean.DividendSummaryBean;
import click.divichart.bean.dto.PieChartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieChartServiceTest {

    private PieChartService pieChartService;

    @BeforeEach
    public void setUp() {
        pieChartService = new PieChartService();
    }

    @Test
    public void testCreateChartDataWithValidData() {
        List<DividendSummaryBean> dividendSummaryBeanList = new ArrayList<>();
        dividendSummaryBeanList.add(new DividendSummaryBean("AAPL", BigDecimal.valueOf(100.01)));
        dividendSummaryBeanList.add(new DividendSummaryBean("GOOG", BigDecimal.valueOf(50.01)));

        PieChartDto chartData = pieChartService.createChartData(dividendSummaryBeanList);

        assertNotNull(chartData);

        // 期待される結果を確認します
        assertEquals("\"AAPL\",\"GOOG\"", chartData.getLabels());
        assertEquals("100.01,50.01", chartData.getChartData());
    }

    @Test
    public void testCreateChartDataWithEmptyList() {
        List<DividendSummaryBean> emptyList = new ArrayList<>();

        PieChartDto chartData = pieChartService.createChartData(emptyList);

        assertNotNull(chartData);

        // 空のリストが渡された場合、空の文字列が返されることを確認します
        assertEquals("\"\"", chartData.getLabels());
        assertEquals("", chartData.getChartData());
    }

}
