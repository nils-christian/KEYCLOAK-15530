package de.rhocas.keycloaktest.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {

	@GetMapping( "/helloWorld" )
	public String helloWorld( ) {
		return "Hello World";
	}

}
