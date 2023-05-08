package config;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//  The @EnableWebSecurity annotation on the class indicates that this configuration will be used to enable Spring Security in the application.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
    }
}

        // /** is added to access any URL with /api/auth/ <anything>
        //  The antMatchers() method specifies which URLs should be permitted without authentication.
        // The third line uses the anyRequest().authenticated() method to require authentication for any other URL patterns that are not explicitly permitted in the antMatchers() method.
