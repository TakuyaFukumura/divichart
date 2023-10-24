package click.divichart.service;

import click.divichart.bean.dto.DividendHistoryDto;
import click.divichart.bean.entity.DividendHistory;
import click.divichart.repository.DividendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditService {

    @Autowired
    DividendHistoryRepository repository;

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
}
