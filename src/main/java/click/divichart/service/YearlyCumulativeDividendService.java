package click.divichart.service;

import click.divichart.repository.DividendHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class YearlyCumulativeDividendService extends DividendService {

    public YearlyCumulativeDividendService(DividendHistoryRepository dividendHistoryRepository) {
        super(dividendHistoryRepository);
    }

    /**
     * 指定された文字列の年を整数として取得します。
     *
     * <p>引数が空文字の場合は、現在の年を返します。それ以外の場合は、文字列をYearとして解析し、
     * その年を整数値として返します。</p>
     *
     * @param targetYear 年を表す文字列。空文字の場合は現在の年を返す。
     * @return 指定された年の整数値。targetYearが空の場合は現在の年。
     */
    public int getTargetYear(String targetYear) {
        if (targetYear.isEmpty()) {
            return Year.now().getValue();
        }
        return Year.parse(targetYear).getValue();
    }

    /**
     * 指定された年の累積配当データを取得します。
     *
     * <p>対象年とユーザー名を指定して、各月の配当額を取得し、それを累積したリストを返します。</p>
     *
     * @param targetYear 配当データを取得する対象の年。
     * @param username   配当データを取得する対象のユーザー名。
     * @return 各月の累積配当額のリスト（1月から12月まで）。
     */
    public List<BigDecimal> getYearlyCumulativeDividendData(int targetYear, String username) {
        List<BigDecimal> monthlyDividend = getMonthlyDividend(targetYear, username);
        List<BigDecimal> cumulativeDividend = new ArrayList<>(monthlyDividend);

        for (int i = 1; i < cumulativeDividend.size(); i++) {
            BigDecimal sumDividend = cumulativeDividend.get(i).add(cumulativeDividend.get(i - 1));
            cumulativeDividend.set(i, sumDividend);
        }
        return Collections.unmodifiableList(cumulativeDividend);
    }
}
