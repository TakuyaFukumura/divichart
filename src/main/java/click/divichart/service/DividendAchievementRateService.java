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

    public DividendAchievementRateService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * グラフ描画用に、配当達成率データを取得する
     *
     * @param numOfYears     対象年数
     * @param targetDividend 目標配当額/月
     * @param username       ユーザ名
     * @return グラフ描画用文字列
     */
    public String getChartData(int numOfYears, String targetDividend, String username) {
        BigDecimal twelveMonths = new BigDecimal("12");
        BigDecimal annualTargetDividend = new BigDecimal(targetDividend).multiply(twelveMonths);
        String[] recentYears = getRecentYearsAsc(numOfYears);

        BigDecimal[] yearlyDividend = new BigDecimal[numOfYears];
        for (int i = 0; i < numOfYears; i++) {
            LocalDate startDate = LocalDate.parse(recentYears[i] + "-01-01");
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            yearlyDividend[i] = repository.getDividendSum(startDate, endDate, username);
        }

        // 達成率を計算する
        BigDecimal[] dividendAchievementRates = new BigDecimal[numOfYears];
        BigDecimal hundred = new BigDecimal("100");
        for (int i = 0; i < numOfYears; i++) {
            if (BigDecimal.ZERO.equals(annualTargetDividend)) {
                log.error("cannot divide by zero");
                return "";
            } else {
                // 目標配当達成率 = 年間配当 * 100 / 年間目標配当
                dividendAchievementRates[i] = yearlyDividend[i].multiply(hundred)
                        .divide(annualTargetDividend, RoundingMode.HALF_UP);
            }
        }

        return createChartData(dividendAchievementRates);
    }

    public List<BigDecimal> getDividendAchievementRateData(List<Integer> pastYears, String targetDividend, String username) {
        BigDecimal twelveMonths = new BigDecimal("12");
        BigDecimal annualGoalDividendAmount = new BigDecimal(targetDividend).multiply(twelveMonths);
        String[] recentYears = getRecentYearsAsc(pastYears.size());

        List<BigDecimal> yearlyDividend = new ArrayList<>();
        for (int i = 0; i < pastYears.size(); i++) {
            LocalDate startDate = LocalDate.parse(recentYears[i] + "-01-01");
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            yearlyDividend.add(repository.getDividendSum(startDate, endDate, username));
        }

        // 達成率を計算する
        List<BigDecimal> dividendAchievementRates = new ArrayList<>();
        for (int i = 0; i < pastYears.size(); i++) {
            if (BigDecimal.ZERO.equals(annualGoalDividendAmount)) {
                log.error("cannot divide by zero");
                return new ArrayList<>();
            } else {
                // 目標配当達成率 = 年間配当 * 100 / 年間目標配当
                dividendAchievementRates.add(yearlyDividend.get(i).multiply(HUNDRED)
                        .divide(annualGoalDividendAmount, RoundingMode.HALF_UP));
            }
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
