package com.config;

import com.handler.MyAuthFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthFailureHandler myAuthFailureHandler;

    /**
     * 设置Spring Security的密码加密器
     * */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security配置
     * and()表示连接，类似SQL语句的and
     * loginPage()指定登录页面，可以是前端的页面
     * loginProcessingUrl()指定登录接口，不需要我们实现登录逻辑，Spring Security内部已经帮我们做了
     * usernameParameter()和passwordParameter()指定登录参数
     * successHandler()，failureHandler()，logoutSuccessHandler()分别表示登录成功、失败、退出登录时要执行的操作
     * permitAll()表示执行上文chain起来的的鉴权信息，包括匿名的和需要权限的
     * csrf()默认是开启的，若开启，需要手动获取csrf_token，本例为了方便对其进行了disable
     * httpBasic()表示开启HTTP basic验证
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/hello").hasRole("admin")
                .and()
                .formLogin()
                .loginPage("/doLogin")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("userNa")
                .passwordParameter("psd")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.getWriter().write("login success!!");
                    }
                })
                .failureHandler(myAuthFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.getWriter().write("logout !!");
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }

    /**
     * 配置不需要Spring Security鉴权的路径
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/**");
    }



}
