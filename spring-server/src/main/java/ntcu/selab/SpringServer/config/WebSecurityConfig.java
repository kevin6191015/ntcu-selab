package ntcu.selab.SpringServer.config;

import ntcu.selab.SpringServer.AuthCheckFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //關掉csrf保護
        http.csrf().disable();
        //不寫session了
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //加上剛剛寫的filter
        http.addFilterBefore(new AuthCheckFilter(), BasicAuthenticationFilter.class);
    }  

}
