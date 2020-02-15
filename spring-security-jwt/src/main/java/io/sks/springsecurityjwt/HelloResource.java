package io.sks.springsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.sks.springsecurityjwt.model.AuthenticationRequest;
import io.sks.springsecurityjwt.model.AuthenticationResponse;
import io.sks.springsecurityjwt.util.JwtUtil;

@RestController
public class HelloResource {
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	@GetMapping("/user")
	public String user() {
		return "Hello user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "Hello admin";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq)throws Exception{
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword() )
					);
			
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException ("Incorrect username or password",e);
		}
		final UserDetails userDetails  = myUserDetailsService.loadUserByUsername(authReq.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
