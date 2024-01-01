package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * 配当増加率グラフ用Service
 */
@Slf4j
@Service
public class DividendIncreaseRateService extends BasicChartService {

    @Autowired
    DividendHistoryRepository repository;

    /**
     * グラフ描画用に、指定年の配当増加率のデータを取得する
     *
     * @param recentYears 近年を表す文字列配列
     * @return グラフ描画用文字列
     */
    public String getChartData(String[] recentYears) {
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal[] rateData = new BigDecimal[recentYears.length];

        for (int i = 0; i < rateData.length; i++) {
            String targetYear = recentYears[rateData.length - 1 - i];
            LocalDate targetYearStartDate = LocalDate.parse(targetYear + "-01-01");
            LocalDate targetYearEndDate = targetYearStartDate.plusYears(1).minusDays(1);
            BigDecimal targetYearsDividend = repository.getDividendSum(targetYearStartDate, targetYearEndDate);

            LocalDate previousYearStartDate = targetYearStartDate.minusYears(1);
            LocalDate previousYearEndDate = previousYearStartDate.plusYears(1).minusDays(1);
            BigDecimal previousYearsDividend = repository.getDividendSum(previousYearStartDate, previousYearEndDate);

            if (BigDecimal.ZERO.equals(previousYearsDividend)) {
                log.error("cannot divide by zero");
                return "";
            } else {
                // 増加率 = (対象年の配当 - 前年の配当) * 100 / 前年の配当
                BigDecimal increaseAmount = targetYearsDividend.subtract(previousYearsDividend);
                rateData[i] = increaseAmount.multiply(hundred).divide(previousYearsDividend, RoundingMode.HALF_UP);
            }
        }
        return createChartData(rateData);
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
