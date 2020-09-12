package kr.ac.hansung.cse.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

/*
 * @Configuration
 * 
 * @EnableWebSecurity public class SecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception { http
 * .authorizeRequests() .anyRequest() .permitAll() .and() .csrf().disable(); } }
 */

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// add our users for in memory authentication

		UserBuilder users = User.withDefaultPasswordEncoder();

		auth.inMemoryAuthentication()
				.withUser(users.username("alice").password("hialice").roles("USER"))
				.withUser(users.username("bob").password("hibob").roles("USER"))
				.withUser(users.username("admin").password("letmein").roles("ADMIN"));

	}

	@Override

	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.
		 *     authorizeRequests() 
		 * 		   .antMatchers("/api/customers/**").hasAnyRole("USER", "ADMIN") 
		 * 		   .antMatchers("/resources/**").permitAll() 
		 *         .and() 
		 *     .formLogin()
		 * 	  	   .loginPage("/showMyLoginPage")
		 * 	   	   .loginProcessingUrl("/authenticateTheUser").permitAll() 
		 *         .and() 
		 *      .logout()
		 *        .permitAll();
		 *        .and()
		 *      .exceptionHandling().accessDeniedPage("/access-denied");
		 */
		
		/*인증절차*/
		http.authorizeRequests()
        	.antMatchers("/api/customers/**").hasAnyRole("USER","ADMIN")
        	.antMatchers("/resources/**").permitAll();
		
		/*계정 없으면 로그인창 리다이렉트*/
		http.formLogin()
			.loginPage("/showMyLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll();
		
		http.logout().permitAll();
		
		/*패스워드 오류 등으로 로그인 실패시 리다이렉트*/
		http.exceptionHandling().accessDeniedPage("/accessDenied");		

	}

}