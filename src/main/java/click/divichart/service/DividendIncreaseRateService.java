package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 配当増加率グラフ用Service
 */
@Slf4j
@Service
public class DividendIncreaseRateService extends DividendService {

    public DividendIncreaseRateService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    public List<BigDecimal> getRateData(List<Integer> pastYears, String username) {
        BigDecimal hundred = new BigDecimal("100");
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
                BigDecimal increaseRate = increaseAmount.multiply(hundred).divide(previousYearsDividend, RoundingMode.HALF_UP);
                rateData.add(increaseRate);
            }
        }
        return rateData;
    }

    /**
     * グラフのラベルを取得する
     *
     * @param recentYears 近年を表す文字列配列
     * @return グラフ描画用ラベル文字列
     */
    public String getLabels(String[] recentYears) {
        StringJoiner labels = new StringJoiner("年\",\"", "\"", "年\"");
        for (int i = recentYears.length - 1; i >= 0; i--) {
            String year = recentYears[i];
            labels.add(year);
        }
        return labels.toString();
    }
}
