package click.divichart.service;


import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DividendServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private DividendService dividendService;

    @BeforeEach
    void setUp() {
        dividendService = new DividendService(repository);
    }

    @Test
    @DisplayName("getPastYears: 指定年数のリストを取得できる")
    void getPastYears() {
        int pastYearsCount = 3;
        int currentYear = LocalDate.now().getYear();
        List<Integer> expected = List.of(currentYear - 2, currentYear - 1, currentYear);

        List<Integer> actual = dividendService.getPastYears(pastYearsCount);

        assertEquals(expected, actual);
    }

    @Test
    void testGetLabels_withMultipleYears() {
        List<Integer> pastYears = Arrays.asList(2021, 2022, 2023);
        String expected = "\"2021年\",\"2022年\",\"2023年\"";
        assertEquals(expected, dividendService.getLabels(pastYears));
    }

    @Test
    void testGetLabels_withSingleYear() {
        List<Integer> pastYears = Collections.singletonList(2023);
        String expected = "\"2023年\"";
        assertEquals(expected, dividendService.getLabels(pastYears));
    }

    @Test
    void testGetLabels_withEmptyList_shouldThrowException() {
        List<Integer> pastYears = Collections.emptyList();
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendService.getLabels(pastYears)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testGetLabels_withNullList_shouldThrowException() {
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendService.getLabels(null)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testCreateChartData() {
        BigDecimal[] testData = { BigDecimal.valueOf(1.23), BigDecimal.valueOf(2.34), BigDecimal.valueOf(3.45) };
        String expectedResult = "1.23,2.34,3.45";

        String chartData = dividendService.createChartData(testData);

        // メソッドが正しい結果を返すかテストします
        assertEquals(expectedResult, chartData);
    }

    @Test
    void testGetRecentYears() {
        List<String> recentYears = dividendService.getRecentYears(5);
        int currentYear = LocalDate.now().getYear();

        // メソッドが正しい結果を返すかテストします
        assertEquals(5, recentYears.size());

        // 正しい年が含まれていることをテストします
        for (int i = 0; i < 5; i++) {
            assertEquals(String.valueOf(currentYear - i), recentYears.get(i));
        }
    }

    @Test
    public void testGetRecentYearsAsc() {
        int currentYear = LocalDate.now().getYear();

        // テストケース1: 5年間
        String[] expectedYears5 = {
                String.valueOf(currentYear - 4),
                String.valueOf(currentYear - 3),
                String.valueOf(currentYear - 2),
                String.valueOf(currentYear - 1),
                String.valueOf(currentYear)
        };
        assertArrayEquals(expectedYears5, dividendService.getRecentYearsAsc(5));

        // テストケース2: 1年間
        String[] expectedYears1 = { String.valueOf(currentYear) };
        assertArrayEquals(expectedYears1, dividendService.getRecentYearsAsc(1));

        // テストケース3: 0年間（空の配列）
        String[] expectedYears0 = {};
        assertArrayEquals(expectedYears0, dividendService.getRecentYearsAsc(0));
    }

    @Test
    void testIsNotYearWithValidYear() {
        assertFalse(dividendService.isNotYear("2023"));
    }

    @Test
    void testIsNotYearWithInvalidYear() {
        assertTrue(dividendService.isNotYear("food")); // 無効な年の文字列
    }

    @Test
    void testIsNotYearWithEmptyString() {
        assertTrue(dividendService.isNotYear("")); // 空の文字列
    }

    @Test
    void testIsNotYearWithTooManyDigits() {
        assertTrue(dividendService.isNotYear("12345")); // 桁数が大きすぎるケース
    }

    @Test
    void testIsNotYearWithLeadingZero() {
        assertTrue(dividendService.isNotYear("0123")); // 先頭が0のケース
    }

}
