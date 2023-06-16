package com.project.springBlog.config;
import com.project.springBlog.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//  The @EnableWebSecurity annotation on the class indicates that this configuration will be used to enable Spring Security in the application.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
    /*

    The method implementation simply calls the authenticationManagerBean() method of the superclass, WebSecurityConfigurerAdapter, using the super keyword.
    The authenticationManagerBean() method in the superclass returns an AuthenticationManager object. By calling it in the subclass and returning the result,
    the method effectively exposes the AuthenticationManager bean for use in other parts of the application.

    */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        /*
        * By using addFilterBefore, you're instructing Spring Security to add your custom JWT authentication filter
        *  before the UsernamePasswordAuthenticationFilter in the filter chain.
        *  This allows your filter to be executed first and handle JWT-based authentication before the standard username/password authentication process.
        * */
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

        // /** is added to access any URL with /api/auth/ <anything>
        //  The antMatchers() method specifies which URLs should be permitted without authentication.
        // The third line uses the anyRequest().authenticated() method to require authentication for any other URL patterns that are not explicitly permitted in the antMatchers() method.
