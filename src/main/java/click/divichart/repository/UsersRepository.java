package click.divichart.repository;

import click.divichart.bean.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ログイン用ユーザ管理テーブル（users）のCRUD操作用クラス
 */
public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
}
