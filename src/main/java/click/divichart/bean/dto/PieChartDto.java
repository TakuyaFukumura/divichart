package click.divichart.bean.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PieChartDto implements Serializable {
    private String labels;
    private String amountReceivedData;

    public PieChartDto(String labels, String amountReceivedData) {
        this.labels = labels;
        this.amountReceivedData = amountReceivedData;
    }
}
