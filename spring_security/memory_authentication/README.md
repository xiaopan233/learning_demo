拉入了Spring Security之后，项目中所有的Servlet，都受Spring Security保护，算是白名单模式。

Spring Security支持Form验证和Http basic验证。默认用户是`user`，密码在控制台窗口会有输出。也可以让Spring Security在`application.properties`或Configuration类配置

`application.properties`中配置：

```properties
spring.security.user.name=ppp
spring.security.user.password=123456
```

Configuration类配置已在本例中写了。路径在`/com/config/SecurityConfig`。配置的东西在`configure(HttpSecurity http)`中有注释。



本例扩展了登录方式，使用Spring web的Filter实现了一个验证码的校验。步骤为：

1. 定义一个Filter，当请求路径是登录接口时，校验验证码
2. 鉴权失败应当抛出`AuthenticationException`，并执行`authenticationFailureHandler.onAuthenticationFailure()`
3. 在Spring Security的`configure`中，将自定义的Filter加入到Spring Security的Filter chain中
