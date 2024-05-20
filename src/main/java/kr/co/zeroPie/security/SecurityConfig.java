package kr.co.zeroPie.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SecurityUserService securityUserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 인증 설정 (로그인)
        httpSecurity.formLogin(login -> login
                                        .loginPage("/member/login")               // login 호출 URL
                                        .defaultSuccessUrl("/")     // login 성공 주소
                                        .failureUrl("/member/login?success=100")  // login 실패 주소
                                        .usernameParameter("userId")               // login시 사용할 name 파라미터
                                        .passwordParameter("userPw")
                                    );

        // 자동로그인 설정
        // rememberMe 쿠키 확인
       // httpSecurity.rememberMe(config -> config.userDetailsService(securityUserService)
       //         .rememberMeParameter("rememberMe")
       //         .key("uniqueAndSecret")
       //         .tokenValiditySeconds(86400));// 자동 로그인 유효 기간 (초)) 24시간

        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                                .invalidateHttpSession(true)            // session 무효화 -> logout 후 새로운 session 시작
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // logout 호출 URL
                                .logoutSuccessUrl("/member/login?success=300"));


        // 인가 설정
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/").permitAll()
                                    //.requestMatchers("/manager/**").hasAnyAuthority("ADMIN", "MANAGER")
                                    .anyRequest().permitAll()
        );

        // 사이트 위변조 방지 설정
        httpSecurity.csrf(CsrfConfigurer::disable);

        // 위 설정들을 return
        return httpSecurity.build();
    }

    // Security 로그인 인증 암호화 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 같은 평문이라도 서로 다른 암호문 생성
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
