package click.divichart.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        // 認証・認可設定
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(
                                mvcMatcherBuilder.pattern("/"),
                                mvcMatcherBuilder.pattern("/login"),
                                mvcMatcherBuilder.pattern("/css/**"),
                                mvcMatcherBuilder.pattern("/createUserAccount/**"),
                                mvcMatcherBuilder.pattern("/lp")
                        ).permitAll()
                        .anyRequest()
                        .authenticated()
        );

        // ログイン設定
        http.formLogin((formLogin) ->                                            // フォーム認証の有効化
                        formLogin.loginPage("/login")                             // ログインフォームを表示するパス
                                .defaultSuccessUrl("/dividendPortfolio")         // 認証成功時の遷移先
                                .failureUrl("/login?error")   // 認証失敗時の遷移先
//                              .loginProcessingUrl("/authenticate")             // フォーム認証処理のパス
//                              .usernameParameter("userName")                   // ユーザ名のリクエストパラメータ名
//                              .passwordParameter("password")                   // パスワードのリクエストパラメータ名
        );

        // ログアウト設定
        http.logout((logout) ->
                logout.logoutSuccessUrl("/lp") // ログアウト成功後の遷移先
                        .permitAll()             // アクセス全許可
        );

        // h2-consoleを表示するためにCSRF対策外へ指定
        http.csrf((csrf) ->
                csrf.ignoringRequestMatchers(
                        AntPathRequestMatcher.antMatcher("/h2-console/**")
                )
        );
        // http.headers().frameOptions().disable();
        http.headers((headers) ->
                headers.frameOptions((frameOptions) ->
                        frameOptions.disable()
                )
        );

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(this.dataSource);
    }
}
