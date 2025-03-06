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
}
