package click.divichart.bean.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class EditForm implements Serializable {
    private Long id;
    private String tickerSymbol; // ティッカー
    private BigDecimal amountReceived; // 配当受取金額
    private Date receiptDate; // 配当受取日
}
