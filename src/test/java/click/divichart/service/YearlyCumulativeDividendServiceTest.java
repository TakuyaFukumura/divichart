package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class YearlyCumulativeDividendServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private YearlyCumulativeDividendService YearlyCumulativeDividendService;

    @BeforeEach
    void setUp() {
        YearlyCumulativeDividendService = new YearlyCumulativeDividendService(repository);
    }
}
