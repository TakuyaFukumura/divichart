package click.divichart.bean.form.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CsvInsertForm implements Serializable {
    private MultipartFile csvFile; // 配当履歴CSVファイル
}
