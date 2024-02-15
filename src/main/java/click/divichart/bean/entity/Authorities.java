package click.divichart.bean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ユーザロール用エンティティ
 * authorities
 */
@Entity
@Getter
@NoArgsConstructor
public class Authorities implements Serializable {
    @Id
    private String username;
    private String authority;

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}
