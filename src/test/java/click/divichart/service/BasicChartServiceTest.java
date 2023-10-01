package click.divichart.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BasicChartServiceTest {

    private BasicChartService basicChartService;

    @BeforeEach
    public void setUp() {
        basicChartService = new BasicChartService();
    }

    @Test
    public void testCreateChartData() {
        BigDecimal[] testData = { BigDecimal.valueOf(1.23), BigDecimal.valueOf(2.34), BigDecimal.valueOf(3.45) };
        String expectedResult = "1.23,2.34,3.45";

        String chartData = basicChartService.createChartData(testData);

        // メソッドが正しい結果を返すかテストします
        assertEquals(expectedResult, chartData);
    }

    @Test
    public void testGetRecentYears() {
        String[] recentYears = basicChartService.getRecentYears();
        int currentYear = LocalDate.now().getYear();

        // メソッドが正しい結果を返すかテストします
        assertEquals(5, recentYears.length);

        // 正しい年が含まれていることをテストします
        for (int i = 0; i < 5; i++) {
            assertEquals(String.valueOf(currentYear - i), recentYears[i]);
        }
    }

    @Test
    void testIsNotYearWithValidYear() {
        assertFalse(basicChartService.isNotYear("2023"));
    }

    @Test
    void testIsNotYearWithInvalidYear() {
        assertTrue(basicChartService.isNotYear("food")); // 無効な年の文字列
    }

    @Test
    void testIsNotYearWithEmptyString() {
        assertTrue(basicChartService.isNotYear("")); // 空の文字列
    }

    @Test
    void testIsNotYearWithTooManyDigits() {
        assertTrue(basicChartService.isNotYear("12345")); // 桁数が大きすぎるケース
    }

    @Test
    void testIsNotYearWithLeadingZero() {
        assertTrue(basicChartService.isNotYear("0123")); // 先頭が0のケース
    }

}
