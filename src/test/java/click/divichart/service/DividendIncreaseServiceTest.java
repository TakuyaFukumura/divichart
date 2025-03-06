package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DividendIncreaseServiceTest {

    private AutoCloseable mocks;
    private DividendIncreaseService dividendIncreaseService;

    @Mock
    private DividendHistoryRepository dividendHistoryRepository;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        dividendIncreaseService = new DividendIncreaseService(dividendHistoryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testGetLabels_withMultipleYears() {
        List<Integer> pastYears = Arrays.asList(2021, 2022, 2023);
        String expected = "\"2021年\",\"2022年\",\"2023年\"";
        assertEquals(expected, dividendIncreaseService.getLabels(pastYears));
    }

    @Test
    void testGetLabels_withSingleYear() {
        List<Integer> pastYears = Collections.singletonList(2023);
        String expected = "\"2023年\"";
        assertEquals(expected, dividendIncreaseService.getLabels(pastYears));
    }

    @Test
    void testGetLabels_withEmptyList_shouldThrowException() {
        List<Integer> pastYears = Collections.emptyList();
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendIncreaseService.getLabels(pastYears)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testGetLabels_withNullList_shouldThrowException() {
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendIncreaseService.getLabels(null)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testGetDividendIncreaseData() {
        // テストデータを準備
        List<Integer> years = Arrays.asList(2022, 2023);
        String username = "testUser";

        BigDecimal dividend2021 = new BigDecimal("500");
        BigDecimal dividend2022 = new BigDecimal("1000");
        BigDecimal dividend2023 = new BigDecimal("1200");

        // モックの振る舞いを定義
        when(dividendHistoryRepository.getDividendSum(any(), any(), eq(username)))
                .thenReturn(dividend2021)  // 2021年の配当金
                .thenReturn(dividend2022)  // 2022年の配当金
                .thenReturn(dividend2022)  // 2022年の配当金
                .thenReturn(dividend2023); // 2023年の配当金

        // 実行
        List<BigDecimal> result = dividendIncreaseService.getDividendIncreaseData(years, username);

        // 結果の検証
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("500"), result.get(0)); // 2022年の増加額
        assertEquals(new BigDecimal("200"), result.get(1)); // 2023年の増加額
    }

    @Test
    void testCreateChartData_withMultipleValues() {
        List<BigDecimal> input = Arrays.asList(
                new BigDecimal("1.23"),
                new BigDecimal("4.56"),
                new BigDecimal("7.89")
        );

        String expected = "1.23,4.56,7.89";
        assertEquals(expected, dividendIncreaseService.createChartData(input));
    }

    @Test
    void testCreateChartData_withSingleValue() {
        List<BigDecimal> input = Collections.singletonList(new BigDecimal("9.99"));

        String expected = "9.99";
        assertEquals(expected, dividendIncreaseService.createChartData(input));
    }

    @Test
    void testCreateChartData_withEmptyList() {
        List<BigDecimal> input = Collections.emptyList();

        String expected = "";
        assertEquals(expected, dividendIncreaseService.createChartData(input));
    }
}
