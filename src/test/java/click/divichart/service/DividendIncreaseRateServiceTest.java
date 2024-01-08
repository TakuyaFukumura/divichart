package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DividendIncreaseRateServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    @Test
    void testGetLabels() {
        // Arrange
        DividendIncreaseRateService dividendIncreaseRateService = new DividendIncreaseRateService(repository);
        String[] recentYears = {"2023", "2022", "2021"};

        // Act
        String result = dividendIncreaseRateService.getLabels(recentYears);

        // Assert
        assertEquals("\"2021年\",\"2022年\",\"2023年\"", result);
    }
}
