package com.filter;

import com.validate.Code;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义Filter
 * 作用：对登录操作进行扩展，本例中扩展一个验证码功能
 * 实现InitializingBean的作用是可以写一个afterPropertiesSet()函数，在SpringBoot获取Bean时，为urlMap设置初始值
 * */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private ArrayList<String> urlMap = new ArrayList<>();
    @Autowired
    private Code codeValidate;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for (String url : urlMap) {
            if (request.getRequestURI().equals(url)){
                String code = request.getParameter("code");
                try{
                    codeValidate.validate(code);
                }catch (AuthenticationException authenticationException){
                    authenticationFailureHandler.onAuthenticationFailure(request, response, authenticationException);
                    return;
                }
                break;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        urlMap.add("/doLogin");
    }
}
