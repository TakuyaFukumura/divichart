package click.divichart.bean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ユーザテーブル用エンティティ
 * users
 */
@Entity
@Getter
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    private String username;
    private String password;
    private boolean enabled;

    public Users(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
