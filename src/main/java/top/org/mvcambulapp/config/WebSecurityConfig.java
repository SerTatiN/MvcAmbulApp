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
                        //  .requestMatchers("/registration").fullyAuthenticated()
                        .requestMatchers("/", "/webjars/**","/doctor/*").permitAll()
                        .requestMatchers("/patient/**").hasRole("ADMIN")
                     //   .requestMatchers("//*").hasRole("PATIENT") записаться

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                            .defaultSuccessUrl("/")
                        .permitAll()
                        .failureUrl("/login?error=true")
                )
                //.logout((logout) -> logout.permitAll());
                .logout().logoutSuccessUrl("/login");
        return http.build();
    }
//    Давайте создадим эту иерархию в Spring Security, просто представив компонент типа RoleHierarchy
//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_ADMIN > ROLE_DOCTOR \n ROLE_DOCTOR > ROLE_USER";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }
//    Чтобы включить эту иерархию ролей в веб-выражения Spring, мы добавляем экземпляр roleHierarchy в обработчик WebSecurityExpressionHandler:
//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy());
//        return expressionHandler;
//    }

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
}
//@Configuration
//public class SecurityConf extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private DatabaseUserDetailService userDetailService;
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailService);
//	}
// @Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/public/**").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.httpBasic();
//	}
//
//}