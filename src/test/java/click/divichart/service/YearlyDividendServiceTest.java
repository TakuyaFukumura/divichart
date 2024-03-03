package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class YearlyDividendServiceTest {
    @Mock
    private DividendHistoryRepository repository;

    @InjectMocks
    private YearlyDividendService yearlyDividendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChartData() {
        // Given
        int currentYear = LocalDate.now().getYear();
        String username = "test";

        when(repository.getDividendSum(
                LocalDate.parse((currentYear - 1) + "-01-01"),
                LocalDate.parse((currentYear - 1) + "-12-31"),
                username)
        ).thenReturn(new BigDecimal("1000"));

        when(repository.getDividendSum(
                LocalDate.parse(currentYear + "-01-01"),
                LocalDate.parse(currentYear + "-12-31"),
                username)
        ).thenReturn(new BigDecimal("800"));

        // When
        String chartData = yearlyDividendService.getChartData(2, username);

        // Then
        assertEquals("1000,800", chartData);
    }

    @Test
    void testGetLabels() {
        // Given
        int currentYear = LocalDate.now().getYear();

        // When
        String labels = yearlyDividendService.getLabels(3);

        // Then
        assertEquals("\"" + (currentYear - 2) + "年\",\"" +
                (currentYear - 1) + "年\",\"" +
                currentYear + "年\"", labels
        );
    }

    @Test
    void testGetRecentYearsAsc() {
        // Given
        int currentYear = LocalDate.now().getYear();

        // When
        String[] recentYears = yearlyDividendService.getRecentYearsAsc(3);

        // Then
        assertEquals(3, recentYears.length);
        assertEquals(String.valueOf(currentYear - 2), recentYears[0]);
        assertEquals(String.valueOf(currentYear - 1), recentYears[1]);
        assertEquals(String.valueOf(currentYear), recentYears[2]);
    }
}
