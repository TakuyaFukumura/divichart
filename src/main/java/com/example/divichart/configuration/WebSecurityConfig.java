package com.example.divichart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 認証・認可設定
        http.authorizeRequests()
                .mvcMatchers("/", "/index", "/login").permitAll()
                .anyRequest().authenticated();

        // ログイン設定
        http.formLogin()                                // フォーム認証の有効化
                .loginPage("/login")                    // ログインフォームを表示するパス
//                .loginProcessingUrl("/authenticate")    // フォーム認証処理のパス
//                .usernameParameter("userName")          // ユーザ名のリクエストパラメータ名
//                .passwordParameter("password")          // パスワードのリクエストパラメータ名
//                .defaultSuccessUrl("/home")             // 認証成功時の遷移先
                .failureUrl("/login?error");   // 認証失敗時の遷移先

        // ログアウト設定
        http.logout()
                .logoutSuccessUrl("/index") // ログアウト成功後の遷移先
                .permitAll();              // アクセス全許可

        // h2-consoleを表示するためにCSRF対策外へ指定
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
        // ユーザーを追加したい時
//        UserDetails user = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$kxhJnySfXtAL6xjlVks36e.NkqIiXCSUHy2Z2zT8HO8jETJ/t6YwK")
//                .roles("USER")
//                .build();
//        users.createUser(user);

        return users;
    }
}
