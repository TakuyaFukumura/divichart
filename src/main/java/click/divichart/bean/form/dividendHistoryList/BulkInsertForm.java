package click.divichart.bean.form.dividendHistoryList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 配当履歴一括登録用フォーム
 */
@Getter
@Setter
@NoArgsConstructor
public class BulkInsertForm implements Serializable {
    private MultipartFile csvFile; // 配当履歴CSVファイル
}
