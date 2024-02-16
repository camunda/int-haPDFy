package org.camunda.internal.hapdfy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  http.authorizeHttpRequests(
	    authorize -> authorize.anyRequest().authenticated()
	  );
	  http.httpBasic(Customizer.withDefaults());
	  http.cors().disable();
	  return http.build();		
	}
}
