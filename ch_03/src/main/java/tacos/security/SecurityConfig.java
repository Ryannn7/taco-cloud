package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用jdbcAuthentication()
     /*   auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("")
                .authoritiesByUsernameQuery("")
                .passwordEncoder(new MyPasswordEncoder());*/

        //使用LDAP服务器进行权限认证
    /*   auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .passwordCompare()
               .passwordEncoder(new BCryptPasswordEncoder())
               .passwordAttribute("passcode")
               .contextSource()
               .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com")
               .root("dc=tacocloud,dc=com");*/


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests().antMatchers("/designe","/orders")
                .hasRole("ROLE_RULE")
                .antMatchers("/","/**").permitAll()*/

        http.authorizeRequests().antMatchers("/designe","orders")
                .access("hasRole()&& T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK)=="+
                "T(java.util.Calendar).TUESDAY")
                .antMatchers("/","/**").access("permitAll");


    }
}
