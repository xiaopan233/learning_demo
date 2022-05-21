若想让Spring Security从数据库中获取用户名密码，需要写一个实现了`UserDetailsService`的类，重写`loadUserByUsername()`，并组装用户名、密码和权限进行返回。本例中该类为`MyUserDetailsService`
