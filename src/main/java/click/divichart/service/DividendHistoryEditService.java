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

    @Autowired
    DividendHistoryRepository repository;

    /**
     * 指定したIDの配当履歴情報を取得する
     *
     * @param id 配当履歴のUK
     * @return 配当履歴情報を詰めたDTO
     */
    @Transactional
    public DividendHistoryDto getDividendHistory(Long id) {
        DividendHistory dividendHistory = repository.getReferenceById(id);
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
     */
    public void save(Long id, String tickerSymbol, BigDecimal amountReceived, Date receiptDate) {
        DividendHistory dividendHistory = new DividendHistory(
                id,
                tickerSymbol,
                amountReceived,
                receiptDate
        );
        repository.save(dividendHistory);
    }
}
