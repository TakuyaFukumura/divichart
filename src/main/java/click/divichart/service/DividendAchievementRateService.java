package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DividendAchievementRateService extends DividendService {

    public static final BigDecimal HUNDRED = new BigDecimal("100");
    public static final BigDecimal MONTHS_IN_YEAR = new BigDecimal("12");


    public DividendAchievementRateService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、配当達成率データを取得する
     *
     * @param pastYears      対象年
     * @param targetDividend 目標配当額/月
     * @param username       ユーザ名
     * @return グラフ描画用文字列
     */
    public List<BigDecimal> getDividendAchievementRateData(List<Integer> pastYears, String targetDividend, String username) {
        BigDecimal annualGoalDividendAmount = new BigDecimal(targetDividend).multiply(MONTHS_IN_YEAR);
        List<Integer> recentYearsAsc = pastYears.stream().sorted().toList();

        List<BigDecimal> yearlyDividends = new ArrayList<>();
        for (Integer year : recentYearsAsc) {
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            BigDecimal yearlyDividend = repository.getDividendSum(startDate, endDate, username);
            yearlyDividends.add(yearlyDividend);
        }

        if (BigDecimal.ZERO.equals(annualGoalDividendAmount)) {
            log.error("cannot divide by zero");
            return new ArrayList<>();
        }

        // 達成率を計算する
        List<BigDecimal> dividendAchievementRates = new ArrayList<>();
        for (BigDecimal yearlyDividend : yearlyDividends) {
            // 目標配当達成率 = 年間配当 / 年間目標配当 * 100
            BigDecimal dividendAchievementRate = yearlyDividend.divide(annualGoalDividendAmount, RoundingMode.HALF_UP);
            dividendAchievementRates.add(dividendAchievementRate.multiply(HUNDRED));
        }

        return dividendAchievementRates;
    }

    /**
     * 円に両替して返す
     *
     * @param targetDividend 目標配当
     * @param rate           ドル円両替レート
     * @return 円換算の目標配当 例）1,234,567
     */
    public String exchange(String targetDividend, String rate) {
        BigDecimal targetDividendYen = new BigDecimal(targetDividend).multiply(new BigDecimal(rate));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(targetDividendYen);
    }
}
