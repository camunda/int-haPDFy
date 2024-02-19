package org.camunda.internal.hapdfy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  http.authorizeHttpRequests(
	    authorize -> authorize
	    	.requestMatchers("/actuator/*").permitAll()
	    	.anyRequest().authenticated()
	  );
	  http.httpBasic(Customizer.withDefaults());
	  http.csrf(csrf -> csrf.disable());
	  http.cors(cors -> cors.disable());
	  return http.build();		
	}
}
