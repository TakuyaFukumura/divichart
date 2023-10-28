package click.divichart.bean.form.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 配当履歴登録用フォーム
 */
@Getter
@Setter
@NoArgsConstructor
public class InsertForm implements Serializable {
    private String tickerSymbol; // ティッカー
    private BigDecimal amountReceived; // 配当受取金額
    private Date receiptDate; // 配当受取日
}
