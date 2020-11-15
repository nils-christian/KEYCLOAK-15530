package de.rhocas.keycloaktest.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public final class Client {

	public static void main( final String[] args ) {
		final Client client = new Client( );
		client.start( );
	}

	private void start( ) {
		final RestTemplate restTemplate = new RestTemplate( );
		final HttpHeaders requestHeaders = new HttpHeaders( );
		requestHeaders.setBasicAuth( "user", "password" );
		final HttpEntity<String> requestEntity = new HttpEntity<>( "", requestHeaders );

		while ( true ) {
			try {
				restTemplate.exchange( "http://localhost:8080/helloWorld", HttpMethod.GET, requestEntity, byte[].class );
				Thread.sleep( 5 );
			} catch ( final Exception ex ) {
				ex.printStackTrace( );
			}
		}
	}

}
