package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class YearlyDividendServiceTest {
    @Mock
    private DividendHistoryRepository dividendHistoryRepository;

    @InjectMocks
    private YearlyDividendService yearlyDividendService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testGetYearlyDividendData() {
        List<Integer> pastYears = Arrays.asList(2020, 2021, 2022);
        String username = "testUser";

        BigDecimal dividend2021 = new BigDecimal("100.50");
        BigDecimal dividend2022 = new BigDecimal("150.75");
        BigDecimal dividend2023 = new BigDecimal("200.25");

        when(dividendHistoryRepository.getDividendSum(any(), any(), eq(username)))
                .thenReturn(dividend2021)  // 2021年の配当金
                .thenReturn(dividend2022)  // 2022年の配当金
                .thenReturn(dividend2023); // 2023年の配当金

        List<BigDecimal> expected = Arrays.asList(
                new BigDecimal("100.50"),
                new BigDecimal("150.75"),
                new BigDecimal("200.25")
        );

        List<BigDecimal> actual = yearlyDividendService.getYearlyDividendData(pastYears, username);

        assertEquals(expected, actual);
    }
}
