package click.divichart.service;



import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CumulativeDividendServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private CumulativeDividendService CumulativeDividendService;

    @BeforeEach
    void setUp() {
        CumulativeDividendService = new CumulativeDividendService(repository);
    }

    @Test
    void testGetCumulativeDividend() {
        BigDecimal[] monthlyDividend = {
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                new BigDecimal("75.00"),
                new BigDecimal("30.00")
        };

        BigDecimal[] cumulativeDividend = CumulativeDividendService.getCumulativeDividend(monthlyDividend);

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
