package top.org.mvcambulapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import top.org.mvcambulapp.model.security.DbUserDetailsService;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers("/","/registration/patient/*", "/webjars/**", "/**","/doctor/list" ).permitAll() //",/schedule/list"
                        .requestMatchers("/patient/**","/person/**").hasRole("ADMIN")//, "/schedule/**"
                        .requestMatchers("/record/record-to-doctor/*","/patient-card/patient").hasRole("PATIENT") //записаться "/schedule/list2",
                        .requestMatchers("/schedule/doctor-list/").hasRole("DOCTOR") //

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                            .defaultSuccessUrl("/",true)
//                        .defaultSuccessUrl("/error?continue")
                        .permitAll()
                        .failureUrl("/login?error=true")
                )
                //.logout((logout) -> logout.permitAll());
                .logout().logoutSuccessUrl("/login");
        return http.build();
    }//
    // Зависимость кодировщика паролей
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(); // стандартный кодировщик Spring
    }

    // КОНФИГУРАЦИЯ ДЛЯ ПОДКЛЮЧЕНИЯ БД
    @Bean
    public DbUserDetailsService userDetailsService() {
        return new DbUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(encoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
    @Bean
    public WebSecurityCustomizer ignoringCustomizer(){
        return(web -> web.ignoring().requestMatchers("/css/**"));
    }
}