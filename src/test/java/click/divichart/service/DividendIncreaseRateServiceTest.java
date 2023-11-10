package click.divichart.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DividendIncreaseRateServiceTest {

    @Test
    void testGetLabels() {
        // Arrange
        DividendIncreaseRateService dividendIncreaseRateService = new DividendIncreaseRateService();
        String[] recentYears = {"2023", "2022", "2021"};

        // Act
        String result = dividendIncreaseRateService.getLabels(recentYears);

        // Assert
        assertEquals("\"2021年\",\"2022年\",\"2023年\"", result);
    }
}
