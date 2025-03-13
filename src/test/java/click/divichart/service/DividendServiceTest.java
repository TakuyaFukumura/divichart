package click.divichart.service;


import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
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
    void testGetTargetYear_EmptyString() {
        // empty stringを渡すと現在の年を取得するテスト
        int currentYear = Year.now().getValue();
        int result = dividendService.getTargetYear("");
        assertEquals(currentYear, result, "現在の年が返されるべきです");
    }

    @Test
    void testGetTargetYear_ValidYear() {
        // 正常な年を文字列で渡すとその年が返されるテスト
        int result = dividendService.getTargetYear("2022");
        assertEquals(2022, result, "指定された年が返されるべきです");
    }

    @Test
    @DisplayName("getLastNYears: 指定年数のリストを取得できる")
    void getLastNYears() {
        int pastYearsCount = 3;
        int currentYear = LocalDate.now().getYear();
        List<Integer> expected = List.of(currentYear - 2, currentYear - 1, currentYear);

        List<Integer> actual = dividendService.getLastNYears(pastYearsCount);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateYearLabels_withMultipleYears() {
        List<Integer> pastYears = Arrays.asList(2021, 2022, 2023);
        String expected = "\"2021年\",\"2022年\",\"2023年\"";
        assertEquals(expected, dividendService.createYearLabels(pastYears));
    }

    @Test
    void testCreateYearLabels_withSingleYear() {
        List<Integer> pastYears = Collections.singletonList(2023);
        String expected = "\"2023年\"";
        assertEquals(expected, dividendService.createYearLabels(pastYears));
    }

    @Test
    void testCreateYearLabels_withEmptyList_shouldThrowException() {
        List<Integer> pastYears = Collections.emptyList();
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendService.createYearLabels(pastYears)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testCreateYearLabels_withNullList_shouldThrowException() {
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> dividendService.createYearLabels(null)
        );
        assertEquals("過去の年のリストが空です", exception.getMessage());
    }

    @Test
    void testCreateChartData_withMultipleValues() {
        List<BigDecimal> input = Arrays.asList(
                new BigDecimal("1.23"),
                new BigDecimal("4.56"),
                new BigDecimal("7.89")
        );

        String expected = "1.23,4.56,7.89";
        assertEquals(expected, dividendService.createChartData(input));
    }

    @Test
    void testCreateChartData_withSingleValue() {
        List<BigDecimal> input = Collections.singletonList(new BigDecimal("9.99"));

        String expected = "9.99";
        assertEquals(expected, dividendService.createChartData(input));
    }

    @Test
    void testCreateChartData_withEmptyList() {
        List<BigDecimal> input = Collections.emptyList();

        String expected = "";
        assertEquals(expected, dividendService.createChartData(input));
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
