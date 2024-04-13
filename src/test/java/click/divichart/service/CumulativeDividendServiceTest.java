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

    private CumulativeDividendService cumulativeDividendService;

    @BeforeEach
    void setUp() {
        cumulativeDividendService = new CumulativeDividendService(repository);
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
    }
}
