package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CumulativeDividendServiceTest {

    @Autowired
    private DividendHistoryRepository repository;

    private CumulativeDividendService cumulativeDividendService;

    @BeforeEach
    void setUp() {
        cumulativeDividendService = new CumulativeDividendService(repository);
    }

    @Test
    void getCumulativeDividend() {
    }

    @Test
    void getLabels() {
    }
}
