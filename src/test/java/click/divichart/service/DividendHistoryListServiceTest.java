package click.divichart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DividendHistoryListServiceTest {

    private DividendHistoryListService dividendHistoryListService;

    @BeforeEach
    public void setUp() {
        dividendHistoryListService = new DividendHistoryListService();
    }

    @Test
    public void testIsTickerSymbolWithValidSymbol() {
        String validSymbol = "AAPL";
        boolean result = dividendHistoryListService.isTickerSymbol(validSymbol);
        assertTrue(result);
    }

    @Test
    public void testIsTickerSymbolWithEmptyString() {
        String emptyString = "";
        boolean result = dividendHistoryListService.isTickerSymbol(emptyString);
        assertFalse(result);
    }

    @Test
    public void testIsTickerSymbolWithInvalidSymbol() {
        String invalidSymbol = "GOOG123";
        boolean result = dividendHistoryListService.isTickerSymbol(invalidSymbol);
        assertFalse(result);
    }

    @Test
    public void testIsTickerSymbolWithLowercaseSymbol() {
        String lowercaseSymbol = "msft";
        boolean result = dividendHistoryListService.isTickerSymbol(lowercaseSymbol);
        assertFalse(result);
    }
}
