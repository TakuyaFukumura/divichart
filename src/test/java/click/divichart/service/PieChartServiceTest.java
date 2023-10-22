package click.divichart.service;

import click.divichart.bean.dto.DividendSummaryDto;
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
        List<DividendSummaryDto> dividendSummaryDtoList = new ArrayList<>();
        dividendSummaryDtoList.add(new DividendSummaryDto("AAPL", BigDecimal.valueOf(100.01)));
        dividendSummaryDtoList.add(new DividendSummaryDto("GOOG", BigDecimal.valueOf(50.01)));

        PieChartDto chartData = pieChartService.createChartData(dividendSummaryDtoList);

        assertNotNull(chartData);

        // 期待される結果を確認します
        assertEquals("\"AAPL\",\"GOOG\"", chartData.getTickerSymbolData());
        assertEquals("100.01,50.01", chartData.getAmountReceivedData());
    }

    @Test
    public void testCreateChartDataWithEmptyList() {
        List<DividendSummaryDto> emptyList = new ArrayList<>();

        PieChartDto chartData = pieChartService.createChartData(emptyList);

        assertNotNull(chartData);

        // 空のリストが渡された場合、空の文字列が返されることを確認します
        assertEquals("\"\"", chartData.getTickerSymbolData());
        assertEquals("", chartData.getAmountReceivedData());
    }

}
