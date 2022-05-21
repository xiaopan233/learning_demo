package com.validate;

import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;

@Component
public class Code {
    /**
     * 模拟验证码校验
     * */
    public boolean validate(String code) throws AuthenticationException {
        if ("aaaa".equals(code)){
            return true;
        }
        //校验不通过返回AuthenticationException
        throw new CodeValidateException("code validate failure");
    }
}

/**
 * 由于AuthenticationException是抽象类，需要自写一个子类
 * AuthenticationException不存在无参构造函数，如果在这打上@Component注解，SpringBoot启动会报错
 * 其实也不需要@Component注解，外部直接用AuthenticationException类型进行操作，内部实例用的CodeValidateException即可
 * */
class CodeValidateException extends AuthenticationException{
    public CodeValidateException(String msg) {
        super(msg);
    }
}