package my.board.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UsersService usersService;
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/board/list").permitAll();
		http.authorizeRequests().antMatchers("/board/view").hasAnyRole("ADMIN","GUEST");
		http.authorizeRequests().antMatchers("/board/register").hasAnyRole("ADMIN","GUEST").antMatchers("/adminpage").hasAnyRole("ADMIN");
		http.formLogin().loginPage("/login");
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true);
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		http.rememberMe().key("remember-me").userDetailsService(usersService).tokenRepository(getJDBCRepository()).tokenValiditySeconds(60*60*24);
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(usersService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PersistentTokenRepository getJDBCRepository() {

		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
