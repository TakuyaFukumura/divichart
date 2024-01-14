package click.divichart.service;



import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class YearlyCumulativeDividendServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private YearlyCumulativeDividendService YearlyCumulativeDividendService;

    @BeforeEach
    void setUp() {
        YearlyCumulativeDividendService = new YearlyCumulativeDividendService(repository);
    }

    @Test
    void testGetCumulativeDividend() {
        BigDecimal[] monthlyDividend = {
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                new BigDecimal("75.00"),
                new BigDecimal("30.00")
        };

        BigDecimal[] cumulativeDividend = YearlyCumulativeDividendService.getCumulativeDividend(monthlyDividend);

        // メソッドが正しい結果を返すかテストします
        assertNotNull(cumulativeDividend);
        assertEquals(monthlyDividend.length, cumulativeDividend.length);

        // 累積データが正しいことをテストします
        BigDecimal cumulativeSum = BigDecimal.ZERO;
        for (int i = 0; i < monthlyDividend.length; i++) {
            cumulativeSum = cumulativeSum.add(monthlyDividend[i]);
            assertEquals(cumulativeSum, cumulativeDividend[i]);
        }
    }
}
