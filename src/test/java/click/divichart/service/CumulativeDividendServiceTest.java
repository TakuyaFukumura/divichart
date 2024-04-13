package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CumulativeDividendServiceTest {

    private CumulativeDividendService cumulativeDividendService;
    private DividendHistoryRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = mock(DividendHistoryRepository.class);
        cumulativeDividendService = new CumulativeDividendService(mockRepository);
    }

    @Test
    void testGetChartData() {
        // モックの振る舞いを設定
        when(mockRepository.getDividendSum(any(), any(), anyString()))
                .thenReturn(BigDecimal.TEN);

        // テストデータの準備
        String[] recentYears = {"2022", "2023", "2024"};
        String username = "testUser";

        // テスト実行
        String chartData = cumulativeDividendService.getChartData(recentYears, username);

        // 検証
        assertNotNull(chartData);
        // ここでchartDataの期待値に対するアサーションを行う
    }

    @Test
    void getCumulativeDividend() {
        // テストデータの準備
        BigDecimal[] yearlyDividend = {BigDecimal.ONE, BigDecimal.TEN, BigDecimal.valueOf(5)};

        // テスト実行
        BigDecimal[] cumulativeDividend = cumulativeDividendService.getCumulativeDividend(yearlyDividend);

        // 検証
        assertNotNull(cumulativeDividend);
        assertEquals(3, cumulativeDividend.length);
        assertEquals(BigDecimal.ONE, cumulativeDividend[0]);
        assertEquals(BigDecimal.valueOf(11), cumulativeDividend[1]);
        assertEquals(BigDecimal.valueOf(16), cumulativeDividend[2]);
    }

    @Test
    void getLabels() {
        // テストデータの準備
        String[] recentYears = {"2022", "2023", "2024"};

        // テスト実行
        String labels = cumulativeDividendService.getLabels(recentYears);

        // 検証
        assertNotNull(labels);
        assertEquals("\"2022年\",\"2023年\",\"2024年\"", labels);
    }
}
