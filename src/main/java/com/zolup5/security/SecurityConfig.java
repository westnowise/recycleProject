package com.zolup5.security;

import com.zolup5.domain.user.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**","/js/**","/img/**", "/font/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable();

        http.authorizeRequests()
                // 페이지 권한 설정 !! 이후에 마이페이지 추가
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/login","/signup").anonymous()
                .anyRequest().permitAll();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        http.logout()
                        .logoutSuccessUrl("/")
                                .permitAll();

        //status code 핸들링
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/login");
                });

        return http.build();
    }

    @Configuration
    public class ThymeleafConfig {
        @Bean
        public SpringTemplateEngine templateEngine() {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver());

            templateEngine.addDialect(new org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect());

            return templateEngine;
        }

        @Bean
        public ITemplateResolver templateResolver() {
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setPrefix("classpath:/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            return templateResolver;
        }

    }
//    public abstract class CustomUserDetails implements UserDetails {
//        private String username;
//        private String password;
//        private String email;
//        private String area;
//        private Collection<? extends GrantedAuthority> authorities;
//
//        public CustomUserDetails(String username, String password, String email,String area, Collection<? extends GrantedAuthority> authorities) {
//            this.username = username;
//            this.password = password;
//            this.email = email;
//            this.area = area;
//            this.authorities = authorities;
//        }
//
//        @Override
//        public String getUsername() {
//            return username;
//        }
//
//        @Override
//        public String getPassword() {
//            return password;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//        public String getArea() {
//            return email;
//        }
//    }

//    public void UserInfo(Member member) {
//        // 현재 사용자의 인증 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            // 인증된 사용자인지 확인하고 UserDetails 객체 가져오기
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) principal;
//                String username = userDetails.getUsername();
//                String email = member.getEmail();
//                String area = member.getArea();
//
//            }
//        }
//    }

//    public class MyCustomUserDetails extends CustomUserDetails  {
//        public MyCustomUserDetails(String username, String password, String email,String area, Collection<? extends GrantedAuthority> authorities) {
//            super(username, password, email, area, authorities);
//        }
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return super.authorities;
//        }
//
//    }


}