package click.divichart.service;

import click.divichart.bean.dto.DividendTotalForStockDto;
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
        List<DividendTotalForStockDto> dividendTotalForStockDtoList = new ArrayList<>();
        dividendTotalForStockDtoList.add(
                new DividendTotalForStockDto(
                        "AAPL",
                        BigDecimal.valueOf(100.01)
                )
        );
        dividendTotalForStockDtoList.add(
                new DividendTotalForStockDto(
                    "GOOG",
                    BigDecimal.valueOf(50.01)
                )
        );

        PieChartDto chartData = pieChartService.createChartData(dividendTotalForStockDtoList);

        assertNotNull(chartData);

        // 期待される結果を確認します
        assertEquals("\"AAPL\",\"GOOG\"", chartData.getTickerSymbolData());
        assertEquals("100.01,50.01", chartData.getAmountReceivedData());
    }

    @Test
    public void testCreateChartDataWithEmptyList() {
        List<DividendTotalForStockDto> emptyList = new ArrayList<>();

        PieChartDto chartData = pieChartService.createChartData(emptyList);

        assertNotNull(chartData);

        // 空のリストが渡された場合、空の文字列が返されることを確認します
        assertEquals("\"\"", chartData.getTickerSymbolData());
        assertEquals("", chartData.getAmountReceivedData());
    }

}
