package click.divichart.service;

import click.divichart.bean.dto.DividendHistoryDto;
import click.divichart.bean.entity.DividendHistory;
import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class DividendHistoryEditService {
    private final DividendHistoryRepository dividendHistoryRepository;

    @Autowired
    public DividendHistoryEditService(DividendHistoryRepository dividendHistoryRepository) {
        this.dividendHistoryRepository = dividendHistoryRepository;
    }

    /**
     * 指定したIDの配当履歴情報を取得する
     *
     * @param id 配当履歴のUK
     * @return 配当履歴情報を詰めたDTO
     */
    @Transactional
    public DividendHistoryDto getDividendHistory(Long id) {
        DividendHistory dividendHistory = dividendHistoryRepository.getReferenceById(id);
        return new DividendHistoryDto(
                dividendHistory.getId(),
                dividendHistory.getTickerSymbol(),
                dividendHistory.getAmountReceived(),
                dividendHistory.getReceiptDate()
        );
    }

    /**
     * 指定したIDの配当履歴情報を更新する
     *
     * @param id             配当履歴ID
     * @param tickerSymbol   ティッカーシンボル
     * @param amountReceived 受取配当金額
     * @param receiptDate    受取日
     * @param username       ユーザ名
     */
    public void save(Long id, String tickerSymbol, BigDecimal amountReceived, Date receiptDate, String username) {
        DividendHistory dividendHistory = new DividendHistory(
                id,
                tickerSymbol,
                amountReceived,
                receiptDate,
                username
        );
        dividendHistoryRepository.save(dividendHistory);
    }
}
