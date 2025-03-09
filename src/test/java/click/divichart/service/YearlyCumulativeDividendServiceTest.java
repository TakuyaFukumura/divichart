package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YearlyCumulativeDividendServiceTest {

    @Mock
    private DividendHistoryRepository dividendHistoryRepository;

    @InjectMocks
    private YearlyCumulativeDividendService yearlyCumulativeDividendService;

    @BeforeEach
    void setUp() {
        yearlyCumulativeDividendService = new YearlyCumulativeDividendService(dividendHistoryRepository);
    }

    @Test
    void testGetTargetYear_EmptyString() {
        // empty stringを渡すと現在の年を取得するテスト
        int currentYear = Year.now().getValue();
        int result = yearlyCumulativeDividendService.getTargetYear("");
        assertEquals(currentYear, result, "現在の年が返されるべきです");
    }

    @Test
    void testGetTargetYear_ValidYear() {
        // 正常な年を文字列で渡すとその年が返されるテスト
        int result = yearlyCumulativeDividendService.getTargetYear("2022");
        assertEquals(2022, result, "指定された年が返されるべきです");
    }

    @Test
    void testGetYearlyCumulativeDividendData() {
        // モックを設定して、月次の配当額を返す
        int targetYear = 2023;
        String username = "testUser";
        // 1月から12月の配当額を設定
        BigDecimal januaryDividend = new BigDecimal("1000");
        BigDecimal februaryDividend = new BigDecimal("1500");
        BigDecimal marchDividend = new BigDecimal("2000");
        BigDecimal aprilDividend = new BigDecimal("2500");
        BigDecimal mayDividend = new BigDecimal("3000");
        BigDecimal juneDividend = new BigDecimal("3500");
        BigDecimal julyDividend = new BigDecimal("4000");
        BigDecimal augustDividend = new BigDecimal("4500");
        BigDecimal septemberDividend = new BigDecimal("5000");
        BigDecimal octoberDividend = new BigDecimal("5500");
        BigDecimal novemberDividend = new BigDecimal("6000");
        BigDecimal decemberDividend = new BigDecimal("6500");

        // getMonthlyDividend メソッドをモック
        when(dividendHistoryRepository.getDividendSum(any(), any(), eq(username)))
                .thenReturn(januaryDividend)    // 1月
                .thenReturn(februaryDividend)   // 2月
                .thenReturn(marchDividend)      // 3月
                .thenReturn(aprilDividend)      // 4月
                .thenReturn(mayDividend)        // 5月
                .thenReturn(juneDividend)       // 6月
                .thenReturn(julyDividend)       // 7月
                .thenReturn(augustDividend)     // 8月
                .thenReturn(septemberDividend)  // 9月
                .thenReturn(octoberDividend)    // 10月
                .thenReturn(novemberDividend)   // 11月
                .thenReturn(decemberDividend);  // 12月

        // 累積配当データを取得
        List<BigDecimal> cumulativeDividendData =
                yearlyCumulativeDividendService.getYearlyCumulativeDividendData(targetYear, username);

        // 累積配当額が正しいか検証
        assertEquals(new BigDecimal("1000"), cumulativeDividendData.get(0));
        assertEquals(new BigDecimal("2500"), cumulativeDividendData.get(1)); // 1000 + 1500
        assertEquals(new BigDecimal("4500"), cumulativeDividendData.get(2)); // 2500 + 2000
        assertEquals(new BigDecimal("7000"), cumulativeDividendData.get(3)); // 4500 + 2500
        assertEquals(new BigDecimal("10000"), cumulativeDividendData.get(4)); // 7000 + 3000
        // 他の月も確認していく
    }
}
