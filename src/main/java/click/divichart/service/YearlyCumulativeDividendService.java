package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class YearlyCumulativeDividendService extends DividendService {

    public YearlyCumulativeDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    public List<BigDecimal> getYearlyCumulativeDividendData(int targetYear, String username) {
        BigDecimal[] monthlyDividend = getMonthlyDividend(targetYear, username);
        BigDecimal[] cumulativeDividend = Arrays.copyOf(monthlyDividend, monthlyDividend.length);

        for (int i = 1; i < cumulativeDividend.length; i++) {
            cumulativeDividend[i] = cumulativeDividend[i].add(cumulativeDividend[i - 1]);
        }
        return Arrays.stream(cumulativeDividend).toList();
    }

    public int getTargetYear(String targetYear) {
        if (targetYear.isEmpty()){
            return Year.now().getValue();
        }
        return Integer.parseInt(targetYear);
    }
}
