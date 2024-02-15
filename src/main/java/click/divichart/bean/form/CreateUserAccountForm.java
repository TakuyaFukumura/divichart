package click.divichart.bean.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * アカウント作成画面用フォーム
 */
@Getter
@Setter
public class CreateUserAccountForm implements Serializable {
    private String username;
    private String password;
}
