package com.madayV2.blog.config;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.authorizeHttpRequests(auth -> auth
        	    .requestMatchers("/", "/posts", "/posts/{id}", "/posts/{id}/data").permitAll()
        	    .requestMatchers("/css/**").permitAll()
        	    .requestMatchers("/sql/**").authenticated()
        	    .anyRequest().authenticated()
        	)
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/posts", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/posts")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin1234"))
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



/*
 * @Configuration //이 클래스는 "설정 파일". 
 * @Bean 메서드들이 여기 모여있다고 Spring에게 알려줌
 * 
 * @EnableWebSecurity //Spring Security를 활성화 //이게 없으면 아래 설정이 아무 효과 없음
 * @Bean //이 메서드가 반환하는 객체를 Spring이 관리하는 Bean으로 등록 
 * 다른 곳에서 @Autowired 하면 여기서 만든
 * 객체가 주입됨
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); // 비밀번호 암호화 객체 } //→ userDetailsService()에서
 * passwordEncoder() 호출하면 //Spring이 이미 만들어둔 Bean을 가져다 씀
 */

