package pl.edu.pjwstk.jaz;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AppWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests ()
               .antMatchers("/testauthority").hasAuthority("admin")
                .antMatchers("/category").hasAuthority("admin")
                .antMatchers("/category/*").hasAuthority("admin")
                .antMatchers("/section").hasAuthority("admin")
                .antMatchers("/section/*").hasAuthority("admin")
                .antMatchers("/auctions").hasAnyAuthority("user","admin")
                .antMatchers("/auctions/*").hasAnyAuthority("user","admin")
                .antMatchers("/userauthority").hasAnyAuthority("user","admin")
                .antMatchers ("/login").permitAll ()
                .antMatchers ("/register").permitAll ()
                .antMatchers ("/auth0/*").permitAll ();
    }
}
