package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 시큐리티 설정 관련 클래스
@EnableWebSecurity // 해당 class 시큐리티를 활성화 한다는 어노테이션
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 시큐리티 설정을 하게 되면 기본적으로 csrf_token 기능이 동작 되는데...이 기능을 끔
        http.authorizeRequests()
                // 해당 경로로 클라이언트가 접속을 하면 인증이 필요
                .antMatchers("/", "/user/**", "/image/**", "subscribe/**", "/comment/**").authenticated()
                .anyRequest().permitAll() // 그 외 다른 경로로 접속은 허용
                .and()
                .formLogin() // 로그인 방식
                .loginPage("/auth/signin") // anyMatchers() 의 경로로 요청이 들어오면 인증이 필요하기에 /auth/signin 경로로 redirect
                .loginProcessingUrl("/auth/signin") // 위와 같은 url 이지만 위는 get, post 방식으로 요청이 오면 해당 프로세스 실행
                .defaultSuccessUrl("/"); // 로그인을 성공 적으로 하면 localhost/ 이동
    }

}
