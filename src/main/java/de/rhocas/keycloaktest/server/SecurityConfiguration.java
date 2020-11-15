package de.rhocas.keycloaktest.server;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@KeycloakConfiguration
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal( final AuthenticationManagerBuilder auth ) throws Exception {
		auth.authenticationProvider( keycloakAuthenticationProvider( ) );
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy( ) {
		return new RegisterSessionAuthenticationStrategy( sessionRegistry( ) );
	}

	// If we register the "SessionRegistryImpl" as a Spring managed bean, the memory leak does no longer occur.
	// @Bean
	SessionRegistryImpl sessionRegistry( ) {
		return new SessionRegistryImpl( );
	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher( ) {
		return new ServletListenerRegistrationBean<>( new HttpSessionEventPublisher( ) );
	}

	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver( ) {
		return new KeycloakSpringBootConfigResolver( );
	}

	@Override
	protected void configure( final HttpSecurity http ) throws Exception {
		super.configure( http );
		http
				.sessionManagement( )
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS )

				.and( )

				.authorizeRequests( )
				.anyRequest( )
				.authenticated( );
	}

}
