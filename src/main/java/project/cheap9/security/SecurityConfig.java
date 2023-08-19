package project.cheap9.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        /* 메모리에 임시 사용자 계정 생성을 위한 객체 생성 */
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        /* Password 암호화 */
        String password = passwordEncoder.encode("1234");

        /* 임시 사용자 계정 생성 */
        manager.createUser(
                User.withUsername("admin")
                .password(password)
                .roles("ADMIN")
                .build()
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // configure HTTP security here ...
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
                .invalidateHttpSession(true); //로그아웃시 생성된 세션 삭제 활성화
        return http.build();
    }

//    public WebSecurityCostomizer webSecurityCostomizer() {
//
//        // configure web security here ...
//    }
}
