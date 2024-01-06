package click.divichart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DividendHistoryListServiceTest {

    private DividendHistoryListService dividendHistoryListService;

    @BeforeEach
    void setUp() {
        dividendHistoryListService = new DividendHistoryListService();
    }

    @Test
    void testIsTickerSymbolWithValidSymbol() {
        String validSymbol = "AAPL";
        boolean result = dividendHistoryListService.isTickerSymbol(validSymbol);
        assertTrue(result);
    }

    @Test
    void testIsTickerSymbolWithEmptyString() {
        String emptyString = "";
        boolean result = dividendHistoryListService.isTickerSymbol(emptyString);
        assertFalse(result);
    }

    @Test
    void testIsTickerSymbolWithInvalidSymbol() {
        String invalidSymbol = "GOOG123";
        boolean result = dividendHistoryListService.isTickerSymbol(invalidSymbol);
        assertFalse(result);
    }

    @Test
    void testIsTickerSymbolWithLowercaseSymbol() {
        String lowercaseSymbol = "msft";
        boolean result = dividendHistoryListService.isTickerSymbol(lowercaseSymbol);
        assertFalse(result);
    }
}
