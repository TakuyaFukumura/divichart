package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 配当増加率グラフ用Service
 */
@Slf4j
@Service
public class DividendIncreaseRateService extends DividendService {

    public static final BigDecimal HUNDRED = new BigDecimal("100");

    public DividendIncreaseRateService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    public List<BigDecimal> getDividendIncreaseRateData(List<Integer> pastYears, String username) {
        List<BigDecimal> rateData = new ArrayList<>();

        for (int targetYear : pastYears) {
            LocalDate targetYearStartDate = LocalDate.of(targetYear, 1, 1);
            LocalDate targetYearEndDate = LocalDate.of(targetYear, 12, 31);
            BigDecimal targetYearsDividend = repository.getDividendSum(
                    targetYearStartDate,
                    targetYearEndDate,
                    username
            );

            LocalDate previousYearStartDate = targetYearStartDate.minusYears(1);
            LocalDate previousYearEndDate = targetYearEndDate.minusYears(1);
            BigDecimal previousYearsDividend = repository.getDividendSum(
                    previousYearStartDate,
                    previousYearEndDate,
                    username
            );

            if (BigDecimal.ZERO.equals(previousYearsDividend)) {
                log.error("cannot divide by zero");
                return new ArrayList<>();
            } else {
                // 増加率 = (対象年の配当 - 前年の配当) * 100 / 前年の配当
                BigDecimal increaseAmount = targetYearsDividend.subtract(previousYearsDividend);
                BigDecimal increaseRate = increaseAmount.multiply(HUNDRED).divide(previousYearsDividend, RoundingMode.HALF_UP);
                rateData.add(increaseRate);
            }
        }
        return rateData;
    }
}
