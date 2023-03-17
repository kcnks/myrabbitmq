package com.javatiaocao.myblog.config;

import com.javatiaocao.myblog.service.Impl.CustomUserServiceImpl;
import com.javatiaocao.myblog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author: IT枫斗者
 * @Date: 2022/6/5 18:45
 * Describe: SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() {
        return new CustomUserServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserService())
                //启动MD5加密
                .passwordEncoder(new PasswordEncoder() {
                    MD5Util md5Util = new MD5Util();

                    @Override
                    public String encode(CharSequence rawPassword) {
                        return md5Util.encode((String) rawPassword);
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return encodedPassword.equals(md5Util.encode((String) rawPassword));
                    }
                });

    }


    //自动登录
    @Autowired
    private DataSource dataSource;

    //自动登录
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/aboutme", "/archives", "/categories", "/friendlylink", "/tags", "/update")
                .permitAll()
                .antMatchers("/editor", "/user").hasAnyRole("USER")
                .antMatchers("/ali", "/mylove").hasAnyRole("ADMIN")
                .antMatchers("/superadmin", "/myheart", "/today", "/yesterday").hasAnyRole("SUPERADMIN")
                .and().rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(300).userDetailsService(customUserService())
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/").usernameParameter("phone").passwordParameter("password")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");


        //自动登录配置          不能写在外面
        http.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(300).userDetailsService(customUserService());


        http.csrf().disable();

    }
}
