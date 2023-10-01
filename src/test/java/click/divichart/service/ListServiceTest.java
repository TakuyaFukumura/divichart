package click.divichart.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListServiceTest {

    private ListService listService;

    @BeforeEach
    public void setUp() {
        listService = new ListService();
    }

    @Test
    public void testIsTickerSymbolWithValidSymbol() {
        String validSymbol = "AAPL";
        boolean result = listService.isTickerSymbol(validSymbol);
        assertTrue(result);
    }

    @Test
    public void testIsTickerSymbolWithEmptyString() {
        String emptyString = "";
        boolean result = listService.isTickerSymbol(emptyString);
        assertFalse(result);
    }

    @Test
    public void testIsTickerSymbolWithInvalidSymbol() {
        String invalidSymbol = "GOOG123";
        boolean result = listService.isTickerSymbol(invalidSymbol);
        assertFalse(result);
    }

    @Test
    public void testIsTickerSymbolWithLowercaseSymbol() {
        String lowercaseSymbol = "msft";
        boolean result = listService.isTickerSymbol(lowercaseSymbol);
        assertFalse(result);
    }
}
