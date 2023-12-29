package com.jwt.controller;

import com.jwt.model.JwtRequest;
import com.jwt.Helper.jwtUtil;
import com.jwt.model.jwtResponse;
import com.jwt.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "*")
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest ) throws Exception {
    System.out.println(jwtRequest);
    try {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (jwtRequest.getUsername(), jwtRequest.getPassword()));

    } catch (UsernameNotFoundException e) {
        e.printStackTrace();
        throw new Exception("Bad Credentials");
    } catch (BadCredentialsException e) {
        e.printStackTrace();
        throw  new Exception("Bad Credentails");
    }
    // fine area

      UserDetails userDetails =  this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
       String token = this.jwtUtil.generateToken(userDetails);
       System.out.println("JWT " + token);

        //{"token" "Value"}
        return ResponseEntity.ok(new jwtResponse(token));
    }

}

