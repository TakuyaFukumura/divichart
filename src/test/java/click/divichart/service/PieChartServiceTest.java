package click.divichart.service;

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
        List<Object[]> dividendSummaryList = new ArrayList<>();
        dividendSummaryList.add(new Object[]{"AAPL", BigDecimal.valueOf(100.01)});
        dividendSummaryList.add(new Object[]{"GOOG", BigDecimal.valueOf(50.01)});

        String[] chartData = pieChartService.createChartData(dividendSummaryList);

        assertNotNull(chartData);
        assertEquals(2, chartData.length);

        // 期待される結果を確認します
        assertEquals("\"AAPL\",\"GOOG\"", chartData[0]);
        assertEquals("100.01,50.01", chartData[1]);
    }

    @Test
    public void testCreateChartDataWithEmptyList() {
        List<Object[]> emptyList = new ArrayList<>();

        String[] chartData = pieChartService.createChartData(emptyList);

        assertNotNull(chartData);
        assertEquals(2, chartData.length);

        // 空のリストが渡された場合、空の文字列が返されることを確認します
        assertEquals("\"\"", chartData[0]);
        assertEquals("", chartData[1]);
    }

}
