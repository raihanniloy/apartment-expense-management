package com.dsi.starter.flutter_p_o_c.rest;

import com.dsi.starter.flutter_p_o_c.Util.JwtUtil;
import com.dsi.starter.flutter_p_o_c.model.JwtRequest;
import com.dsi.starter.flutter_p_o_c.model.JwtResponse;
import com.dsi.starter.flutter_p_o_c.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException ex){
            System.out.println("############ Incorrect username or Password #############");
            ex.printStackTrace();
            return  ResponseEntity.ok(new JwtResponse("Failed",null, false, "Incorrect username or Password"));
        }

       final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
       final String jwt = jwtUtil.generateToken(userDetails);
       final boolean admin = userDetails.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN") || r.getAuthority().equals("ROLE_MANAGER"));

        return  ResponseEntity.ok(new JwtResponse("Successful", jwt, admin, null));
    }
}
